package com.primeengineer.smol.security;

import com.primeengineer.smol.service.UserDetailsServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;


/**
 * Configuration class for Spring Security.
 * <p>
 * Sets up the security filter chain, JWT authentication filter,
 * and authentication provider with custom {@link UserDetailsServiceImpl}.
 * </p>
 */
@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Value("${frontend.url}")
    private String frontendUrl;
    /**
     * Configures the HTTP security for the application.
     * - Disables CSRF
     * - Permits unauthenticated access to /api/auth/** and /{shorturl}
     * - Requires authentication for all other endpoints
     * - Registers JWT authentication filter
     */
    @Bean
    public SecurityFilterChain getFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/{shorturl}").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Provides a {@link PasswordEncoder} bean using BCrypt.
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures and provides the {@link AuthenticationProvider} bean.
     * Uses a DAO-based authentication provider with custom user details and password encoder.
     * @return the authentication provider
     */
    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * Provides the {@link AuthenticationManager} bean used by Spring Security
     * to handle authentication requests.
     * @return the authentication manager
     */
    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration cofig) throws Exception {
        return cofig.getAuthenticationManager();
    }

    /**
     * Configures and provides the CORS (Cross-Origin Resource Sharing) settings for the application.
     * <p>
     * Allows requests from the frontend (e.g., http://localhost:5173) to access the backend by:
     * <ul>
     *     <li>Allowing specific origins (e.g., local frontend development server)</li>
     *     <li>Permitting HTTP methods such as GET, POST, PUT, DELETE, and OPTIONS</li>
     *     <li>Allowing all headers</li>
     *     <li>Enabling credentials (e.g., cookies, authorization headers)</li>
     * </ul>
     * Applies these settings to all paths (/**).
     * </p>
     *
     * @return a {@link CorsConfigurationSource} bean with the defined CORS policy
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("https://smoltech.shop",
                "http://localhost:5173" ));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(List.of("*"));
        cors.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", cors);
        return corsConfigurationSource;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource())
        );
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Important!
        return bean;
    }
}
