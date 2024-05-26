package com.app.backend.security.filter

import com.app.backend.security.service.AuthenticationService
import com.app.backend.security.utils.CookieUtils
import com.app.backend.security.utils.TokenUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

/**
 * @author Vladislav Nasevich
 */
@Component
class AccessTokenExpirationFilter(
    val tokenUtils: TokenUtils,
    val authenticationService: AuthenticationService
) : OncePerRequestFilter() {

    /**
     * Same contract as for `doFilter`, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See [.shouldNotFilterAsyncDispatch] for details.
     *
     * Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     */
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessToken = tokenUtils.getAccessTokenFromCookies(request)

            if (!accessToken.isNullOrEmpty() && !tokenUtils.validateToken(accessToken)) {
                val d = authenticationService.updateAccessToken(request).accessTokenCookie
                response.addCookie(CookieUtils.createCookieFromString(d))
            }
        } catch (e: Exception) {
            logger.error("Error while filtering access token", e)
        }

        filterChain.doFilter(request, response)
    }
}
