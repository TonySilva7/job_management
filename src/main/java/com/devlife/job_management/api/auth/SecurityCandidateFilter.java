package com.devlife.job_management.api.auth;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.devlife.job_management.domain.service.providers.JWTCandidateProvider;
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
public class SecurityCandidateFilter extends OncePerRequestFilter {

    private JWTCandidateProvider jwtCandidateProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {


        if (request.getRequestURI().startsWith("/candidates")) {
            SecurityContextHolder.getContext().setAuthentication(null);
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null) {
                DecodedJWT token = this.jwtCandidateProvider.validateToken(authHeader);

                System.out.println("=========== TOKEN =============");
                System.out.println(token);

                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("candidate_id", token.getSubject());

                var roles = token.getClaim("roles").asList(Object.class);

                var authorities = roles.stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role.toString())).toList();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }


        filterChain.doFilter(request, response);

        System.out.println("=========== CHEGOU AO FINAL =============");
    }
}
