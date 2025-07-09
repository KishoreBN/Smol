package com.primeengineer.smol.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * JwtAuthenticationFilter is a custom Spring Security filter that intercepts each HTTP request
 * and checks for a valid JWT in the Authorization header. If a valid token is found,
 * it sets the user authentication in the Spring Security context.
 * <p>
 * This filter extends {@link OncePerRequestFilter} to ensure it is only executed once per request.
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
             jwtToken = jwtToken.substring(7);
             if (jwtUtil.validateToken(jwtToken)) {
                 String username = jwtUtil.extractUsername(jwtToken);
                 UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, null);
                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
             }
        }
        filterChain.doFilter(request, response);
    }
}
