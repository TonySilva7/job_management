package com.devlife.job_management.api.auth;

import com.devlife.job_management.domain.service.providers.JWTCompanyProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Component
@AllArgsConstructor
public class SecurityFilterCompany extends OncePerRequestFilter {

    private JWTCompanyProvider jwtCompanyProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {


        if (request.getRequestURI().startsWith("/companies")) {
            SecurityContextHolder.getContext().setAuthentication(null);
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null) {
                var token = this.jwtCompanyProvider.validateToken(authHeader);

                if (token == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");

                    return;
                }

                String subjectToken = token.getSubject();

                request.setAttribute("company_id", subjectToken);

                var roles = token.getClaim("roles").asList(Object.class);

                var authorities = roles.stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role.toString())).toList();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subjectToken, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
