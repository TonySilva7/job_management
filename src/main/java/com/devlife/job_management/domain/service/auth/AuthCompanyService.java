package com.devlife.job_management.domain.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.devlife.job_management.api.model.AuthCompanyDTO;
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

    public String authenticate(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        String username = authCompanyDTO.getUsername();
        String rawPassword = authCompanyDTO.getPassword();

        Company company = companyRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Companhia não encontrada"));

        String encodedPassword = company.getPassword();

        boolean passwordMatches = passwordEncoder.matches(rawPassword, encodedPassword);

        if (!passwordMatches) {
            throw new AuthenticationException("Usuário ou Senha inválida");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create().withIssuer("devlife vagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(company.getId().toString())
                .withClaim("role", "COMPANY")
                .sign(algorithm);
    }
}
