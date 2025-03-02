package com.example.shelfmate.config;

import com.example.shelfmate.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SecurityConfig {



    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // Swagger configuration
                        .requestMatchers("/swagger-ui/**").permitAll()  // Allow swagger-ui resources
                        .requestMatchers("/v3/api-docs/**").permitAll() // Allow OpenAPI docs access
                        .requestMatchers("/swagger-ui/index.html").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/email/verify-otp/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/email/resend-otp/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/logout/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/users/all-users").hasRole("ADMIN")

                        // Password reset
                        .requestMatchers(HttpMethod.POST, "/api/password-reset/request/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/password-reset/reset/**").permitAll()

                        // Authentication docs
                        .requestMatchers(HttpMethod.GET, "/authentication-docs/**").permitAll()

                        // Authenticated-only endpoints for user profile management
                        .requestMatchers(HttpMethod.PUT, "/api/v1/user/profile/change-username").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/user/profile/change-email").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/profile/change-password").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/profile/upload-photo").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/profile/delete-user").authenticated()

                        .anyRequest().authenticated())
                .authenticationManager(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
