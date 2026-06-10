package com.wgu.taskmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the application.
 * Demonstrates industry-appropriate security features:
 * - BCrypt password encoding
 * - Form-based authentication
 * - Role-based authorization
 * - CSRF protection
 * - Session management
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    /**
     * Configure BCrypt password encoder
     * Strength 12 provides strong encryption for passwords
     * Demonstrates security requirement: passwords are encrypted
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
    /**
     * Configure HTTP security
     * Demonstrates security requirements:
     * - Authentication required for protected resources
     * - Role-based access control
     * - CSRF protection enabled
     * - Session management configured
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Configure authorization rules
            .authorizeHttpRequests(auth -> auth
                // Public endpoints - no authentication required
                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/h2-console/**").permitAll()
                // Admin-only endpoints
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Protected endpoints - authentication required
                .requestMatchers("/tasks/**", "/projects/**", "/reports/**").authenticated()
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            // Configure form login
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/tasks", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            // Configure logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            // Configure session management
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            )
            // Enable CSRF protection (enabled by default)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            // Allow H2 console in frames (for development only)
            .headers(headers -> headers
                .frameOptions().sameOrigin()
            );
        
        return http.build();
    }
}
