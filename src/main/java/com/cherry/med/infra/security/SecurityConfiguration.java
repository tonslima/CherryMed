package com.cherry.med.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    // RESTful configuration and authentication roles
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection for REST APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless JWT authentication
                .authorizeHttpRequests(authorize -> {

                    // Public endpoints (No authentication required)
                    authorize.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/auth/register").permitAll();

                    // Grants ADMIN access to all endpoints
                    authorize.requestMatchers("/**").hasRole("ADMIN");

                    // Grants DOCTOR access
                    authorize.requestMatchers(HttpMethod.POST, "/doctors").hasRole("DOCTOR");
                    authorize.requestMatchers(HttpMethod.GET, "/doctors/{id}").hasAnyRole("DOCTOR", "PATIENT");
                    authorize.requestMatchers(HttpMethod.GET, "/doctors").hasAnyRole("DOCTOR", "PATIENT");
                    authorize.requestMatchers(HttpMethod.PATCH, "/doctors/**").hasRole("DOCTOR");
                    authorize.requestMatchers(HttpMethod.DELETE, "/doctors/{id}").hasRole("DOCTOR");

                    // Grants PATIENT access
                    authorize.requestMatchers(HttpMethod.POST, "/patients").hasRole("PATIENT");
                    authorize.requestMatchers(HttpMethod.GET, "/patients/*").hasAnyRole("PATIENT", "DOCTOR");
                    authorize.requestMatchers(HttpMethod.GET, "/patients").hasAnyRole("PATIENT", "DOCTOR");
                    authorize.requestMatchers(HttpMethod.PATCH, "/patients/**").hasRole("PATIENT");
                    authorize.requestMatchers(HttpMethod.DELETE, "/patients/*").hasRole("PATIENT");

                    // All other requests require authentication
                    authorize.anyRequest().authenticated();
                }).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    // Makes Spring know AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Makes Spring know BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}