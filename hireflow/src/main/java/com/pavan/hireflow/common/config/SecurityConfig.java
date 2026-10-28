package com.pavan.hireflow.common.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/register/**", "/auth/login", "/error", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                .permitAll()

                                .requestMatchers(HttpMethod.GET, "/jobs/**")
                                .permitAll()

                                .requestMatchers(HttpMethod.POST, "/jobs/**")
                                .hasRole("RECRUITER")

                                .requestMatchers(HttpMethod.PUT, "/jobs/**")
                                .hasRole("RECRUITER")

                                .requestMatchers(HttpMethod.PATCH, "/jobs/**")
                                .hasRole("RECRUITER")

                // Testing Candidate authorization like if candidate is allowed or What are you allowed to do?
//                        .requestMatchers("/auth/candidate-test")
//                        .hasRole("CANDIDATE")
//                        .requestMatchers("/auth/recruiter-test")
//                        .hasRole("RECRUITER")
                .anyRequest()
                .authenticated()
        )
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                (request, response, authException) -> {
                                    System.out.println(
                                            "===== AUTHENTICATION ENTRY POINT ====="
                                    );
                                    System.out.println(
                                            "Exception: " + authException.getMessage()
                                    );
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                                })
                        .accessDeniedHandler(
                                (request, response, accessDeniedException) -> {
                                    System.out.println(
                                            "===== ACCESS DENIED HANDLER ====="
                                    );
                                    System.out.println(
                                            "Exception: "
                                                    + accessDeniedException.getMessage()
                                    );

                                    response.sendError(
                                            HttpServletResponse.SC_FORBIDDEN
                                    );
                                }

                        )
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    };


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
