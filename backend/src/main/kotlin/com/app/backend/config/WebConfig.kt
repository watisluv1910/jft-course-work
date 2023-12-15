package com.app.backend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Configures Cross-Origin Resource Sharing (CORS) for the application.
 */
@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    @Value("\${tsr.app.frontend-port}")
    private val frontendPort: String? = null

    @Value("\${tsr.app.dashboard-port}")
    private val dashboardPort: String? = null

    /**
     * Configures CORS mappings for the specified endpoints.
     *
     * @param registry [CorsRegistry] to configure.
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/api/**")
            .allowedOriginPatterns(
                "http://localhost:$frontendPort"
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .maxAge(1800)
            .allowedHeaders("*")
            .allowCredentials(true)

        registry
            .addMapping("/management/**")
            .allowedOriginPatterns(
                "http://localhost:$dashboardPort",
                "http://dashboard:$dashboardPort"
            )
            .allowedMethods("GET")
            .maxAge(300)
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}
