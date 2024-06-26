package com.devlife.job_management.api.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final SecurityFilterCompany securityFilterCompany;

    private final SecurityCandidateFilter securityCandidateFilter;

    public SecurityConfig(SecurityFilterCompany securityFilterCompany, SecurityCandidateFilter securityCandidateFilter) {
        this.securityFilterCompany = securityFilterCompany;
        this.securityCandidateFilter = securityCandidateFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/candidates/signup").permitAll()
                        .requestMatchers("/companies/signup").permitAll()
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers("/companies/**").authenticated()
                        .requestMatchers("/candidates/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityFilterCompany, BasicAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
