package com.app.backend.security.utils

import jakarta.servlet.http.Cookie
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author Vladislav Nasevich
 */
class CookieUtils {
    companion object {
        private fun parseHttpDate(dateString: String): Long {
            val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
            dateFormat.timeZone = TimeZone.getTimeZone("GMT")
            val date = dateFormat.parse(dateString)
            return date.time
        }

        fun createCookieFromString(cookieString: String): Cookie {
            val parts = cookieString.split(";").map { it.trim() }
            val cookieNameValue = parts[0].split("=")
            val cookieName = cookieNameValue[0]
            val cookieValue = cookieNameValue[1]

            val cookie = Cookie(cookieName, cookieValue)

            for (part in parts.drop(1)) {
                when {
                    part.startsWith("Path=") -> cookie.path = part.substringAfter("Path=")
                    part.startsWith("Max-Age=") -> cookie.maxAge = part.substringAfter("Max-Age=").toInt()
                    part.startsWith("Expires=") -> {
                        val expiresDate = part.substringAfter("Expires=")
                        val expiresMillis = parseHttpDate(expiresDate)
                        val maxAge = (expiresMillis - Date().time) / 1000
                        cookie.maxAge = maxAge.toInt()
                    }
                    part == "Secure" -> cookie.secure = true
                    part == "HttpOnly" -> cookie.isHttpOnly = true
                }
            }

            return cookie
        }
    }
}
