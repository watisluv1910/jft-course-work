package com.app.backend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Configures Cross-Origin Resource Sharing (CORS) for the application.
 *
 * @author Vladislav Nasevich
 */
@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    @Value("\${tsr.app.frontend-port}")
    private val frontendPort: String? = null

    /**
     * Configures CORS mappings for the specified endpoints.
     *
     * @param registry [CorsRegistry] to configure.
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/api/**")
            .allowedOriginPatterns(
                "http://localhost:$frontendPort",
                "http://95.164.7.153:$frontendPort", // TODO: Rework
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .maxAge(1800)
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}
