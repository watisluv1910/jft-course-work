package com.app.backend.security.utils

import com.app.backend.model.user.User
import com.app.backend.model.user.UserDetailsImpl
import com.app.backend.model.user.token.UserRefreshToken
import com.app.backend.repo.UserRefreshTokenRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import java.math.BigInteger
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.crypto.SecretKey

/**
 * Utility class for managing JWT tokens and cookies related to user authentication.
 *
 * @property refreshTokenRepository repository for managing user [refresh tokens][UserRefreshToken].
 * @author Vladislav Nasevich
 */
@Component
class TokenUtils(
    private val refreshTokenRepository: UserRefreshTokenRepository
) {
    @Value("\${tsr.app.sign-key}")
    lateinit var signKey: String

    @Value("\${tsr.app.token-expiration-ms}")
    lateinit var accessTokenExpirationMs: BigInteger

    @Value("\${tsr.app.refresh-token-expiration-ms}")
    lateinit var refreshTokenExpirationMs: BigInteger

    @Value("\${tsr.app.jwt-cookie-name}")
    lateinit var jwtCookieName: String

    @Value("\${tsr.app.jwt-refresh-cookie-name}")
    lateinit var jwtRefreshCookieName: String

    private val logger: Logger = LoggerFactory.getLogger(TokenUtils::class.java)

    /**
     * Generates a JWT access token stored in a cookie for a given [User].
     *
     * @param user user for whom the access token is to be generated.
     * @return [ResponseCookie] containing the JWT access token.
     */
    fun generateAccessTokenCookie(user: User): ResponseCookie {
        return generateAccessTokenCookie(UserDetailsImpl.build(user))
    }

    /**
     * Generates a JWT access token stored in a cookie for a given [UserDetailsImpl] object.
     *
     * @param userDetails [UserDetailsImpl] to generate a token for.
     * @return [ResponseCookie] containing the JWT access token.
     */
    fun generateAccessTokenCookie(userDetails: UserDetailsImpl): ResponseCookie {
        return generateCookie(
            jwtCookieName,
            generateAccessToken(userDetails),
            "/api"
        )
    }

    /**
     * Generates a JWT refresh token stored in a cookie for a given [UserDetailsImpl] object and [User].
     *
     * @param userDetails [UserDetailsImpl] to generate a token for.
     * @param user user associated with the refresh token.
     * @return [ResponseCookie] containing the JWT refresh token.
     */
    fun generateRefreshTokenCookie(userDetails: UserDetailsImpl, user: User): ResponseCookie {
        return generateCookie(
            jwtRefreshCookieName,
            updateRefreshToken(userDetails, user).token,
            "/api"
        )
    }

    private fun generateCookie(name: String, value: String, path: String): ResponseCookie {
        return ResponseCookie
            .from(name, value)
            .path(path)
            .maxAge(refreshTokenExpirationMs.toLong() / 1000)
            .httpOnly(true)
            .secure(true)
            .build()
    }

    /**
     * Retrieves the value of a cookie with the specified name from the request.
     *
     * @param request [HttpServletRequest] containing cookies.
     * @param name name of the cookie to retrieve.
     * @return value of the cookie if found, otherwise null.
     */
    private fun getValueFromCookies(request: HttpServletRequest, name: String): String? {
        val cookie = WebUtils.getCookie(request, name)
        return cookie?.value
    }

    /**
     * Retrieves the access token from cookies in the provided [HttpServletRequest].
     *
     * @param request [HttpServletRequest] which include a JWT token in the cookies.
     * @return access token if found in the cookies, otherwise null.
     */
    fun getAccessTokenFromCookies(request: HttpServletRequest): String? =
        getValueFromCookies(request, jwtCookieName)

    /**
     * Retrieves the refresh token from cookies in the provided [HttpServletRequest].
     *
     * @param request [HttpServletRequest] containing cookies.
     * @return refresh token if found in the cookies, otherwise null.
     */
    fun getRefreshTokenFromCookies(request: HttpServletRequest): String? =
        getValueFromCookies(request, jwtRefreshCookieName)

    fun extractUsername(token: String?): String {
        return extractClaim(token) { claim: Claims? -> claim?.subject ?: "" }
    }

    /**
     * Retrieves a specific JWT claim using the provided resolver.
     *
     * @param token JWT token to validate.
     * @param claimsResolver resolver function to extract a specific claim from the token.
     * @return resolved claim.
     */
    private fun <T> extractClaim(token: String?, claimsResolver: (Claims?) -> T): T {
        val claims = extractAllClaims(token!!)
        return claimsResolver(claims)
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token JWT token.
     * @return extracted claims.
     */
    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getSecretKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(signKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    /**
     * Generates a JWT token with specified claims and expiration time.
     *
     * @param extraClaims Additional claims to include in the token.
     * @param userDetails user details for whom the token is generated.
     * @param expirationTimeMs expiration time of the token in milliseconds.
     * @return generated JWT token.
     */
    private fun generateToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetailsImpl,
        expirationTimeMs: BigInteger
    ): String = Jwts
        .builder()
        .claims(extraClaims)
        .subject(userDetails.username)
        .issuedAt(Date())
        .expiration(
                Date.from(
                    LocalDateTime
                        .now()
                        .plusSeconds(expirationTimeMs.toLong() / 1000)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
            )
        )
        .signWith(getSecretKey(), Jwts.SIG.HS256)
        .compact()

    private fun generateAccessToken(userDetails: UserDetailsImpl): String {
        return generateToken(
            mapOf(
                "roles" to userDetails.authorities?.map { it.authority }!!,
                "email" to userDetails.email!!
            ),
            userDetails,
            accessTokenExpirationMs
        )
    }

    private fun generateRefreshToken(userDetails: UserDetailsImpl): String {
        return generateToken(
            mapOf(),
            userDetails,
            refreshTokenExpirationMs
        )
    }

    /**
     * Updates the refresh token for the specified user details and user.
     *
     * If [UserRefreshToken] is present, updates [token][UserRefreshToken.token]
     * and [dateExpiration][UserRefreshToken.expirationDate] fields,
     * otherwise saves new entity into [UserRefreshTokenRepository].
     *
     * @param userDetails user details for whom the refresh token is updated.
     * @param user user entity for whom the refresh token is updated.
     * @return updated user [refresh token][UserRefreshToken].
     */
    fun updateRefreshToken(
        userDetails: UserDetailsImpl,
        user: User
    ): UserRefreshToken {
        return refreshTokenRepository.findByUser(user)?.let {
            refreshTokenRepository.save(
                it.apply {
                    this.token = generateRefreshToken(userDetails)
                    this.expirationDate = Timestamp(System.currentTimeMillis() + refreshTokenExpirationMs.toLong())
                }
            )
        } ?: refreshTokenRepository.save(
                UserRefreshToken().apply {
                    this.token = generateRefreshToken(userDetails)
                    this.user = user
                    this.expirationDate = Timestamp(System.currentTimeMillis() + refreshTokenExpirationMs.toLong())
                }
            )

    }

    /**
     * Validates the provided JWT token.
     *
     * @param token JWT token to validate.
     * @return true if the token is valid, otherwise false.
     */
    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: ${e.message}")
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: ${e.message}")
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: ${e.message}")
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: ${e.message}")
        }
        return false
    }
}
