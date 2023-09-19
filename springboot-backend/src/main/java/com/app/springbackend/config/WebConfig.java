package com.app.springbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Cross-Origin Resource Sharing (CORS) for the application.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    /**
     * Adds CORS mappings for the entire application, allows all headers and credentials.
     * The maximum age for pre-flight requests - 1800 seconds.
     *
     * @param registry the {@link CorsRegistry} to add mappings to.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns(
                        "http://localhost:3000",
                        "http://localhost:3001"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .maxAge(1800)
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
