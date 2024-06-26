package com.devlife.job_management.domain.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.devlife.job_management.api.model.AuthCompanyDTO;
import com.devlife.job_management.api.model.AuthCompanyResDTO;
import com.devlife.job_management.domain.model.Company;
import com.devlife.job_management.domain.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCompanyService {
    @Value("${security.token.secret.company}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResDTO authenticate(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        String username = authCompanyDTO.getUsername();
        String rawPassword = authCompanyDTO.getPassword();

        Company company = companyRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Companhia não encontrada"));

        String encodedPassword = company.getPassword();

        boolean passwordMatches = passwordEncoder.matches(rawPassword, encodedPassword);

        if (!passwordMatches) {
            throw new AuthenticationException("Usuário ou Senha inválida");
        }

        Instant expiresIn = Instant.now().plus(Duration.ofHours(2));

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        String jwtToken = JWT.create().withIssuer("devlife vagas")
                .withExpiresAt(expiresIn)
                .withSubject(company.getId().toString())
                .withClaim("roles", List.of("COMPANY"))
                .sign(algorithm);

        return AuthCompanyResDTO.builder().access_token(jwtToken).expires_in(expiresIn.toEpochMilli()).build();
    }
}
