package com.devlife.job_management.domain.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.devlife.job_management.api.model.AuthCandidateResDTO;
import com.devlife.job_management.api.model.AuthCandidateDTO;
import com.devlife.job_management.domain.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;


@Service
public class AuthCandidateService {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResDTO authenticate(AuthCandidateDTO authCandidateDTO) throws AuthenticationException {
        var candidate = candidateRepository.findByUsername(authCandidateDTO.username())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário ou Senha incorretos"));

        var passwordMatches = this.passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException("Usuário ou Senha incorretos");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant expiresIn = Instant.now().plus(Duration.ofMinutes(10));

        var token = JWT.create()
                .withIssuer("devlife vagas")
                .withExpiresAt(expiresIn)
                .withClaim("roles", List.of("CANDIDATE"))
                .withSubject(candidate.getId().toString())
                .sign(algorithm);

        return AuthCandidateResDTO.builder().access_token(token).expires_in(expiresIn.toEpochMilli()).build();
    }
}
