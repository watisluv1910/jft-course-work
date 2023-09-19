package com.app.springbackend.security.config;

import com.app.springbackend.security.jwt.JwtAuthEntryPoint;
import com.app.springbackend.security.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 Default configuration class for Spring Security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthEntryPoint unauthorizedHandler;

    /**
     Configures the security filter chain.
     @param httpSecurity the {@link HttpSecurity} object to configure.
     @return the configured {@link SecurityFilterChain} object.
     @throws Exception if an error occurs during the configuration process.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // TODO: Remove at production stage
                .exceptionHandling(
                        (exceptionHandling) -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(
                        (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorizeHttpRequest) ->
                        authorizeHttpRequest
                                .requestMatchers("/api/profile/**", "/api/test/**").authenticated()
                                .anyRequest().permitAll()
                ) // TODO: Rework white endpoints
//                .formLogin((formLogin) ->
//                        formLogin
//                                .usernameParameter("username")
//                                .passwordParameter("password")
//                                .loginPage("/api/auth/login")
//                                .failureUrl("/api/auth/login?failed")
//                                .loginProcessingUrl("/api/auth/login/process")
//                ) // TODO: Check if it is actually necessary to be used
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
