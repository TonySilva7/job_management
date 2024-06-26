package com.devlife.job_management.domain.service.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTCompanyProvider {

    @Value("${security.token.secret.company}")
    private String secretKeyCompany;

    public String validateToken(String token) {

        token = token.replace("Bearer ", "");


        Algorithm algorithm = Algorithm.HMAC256(secretKeyCompany);

        try {
            String tokenWithId = JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();

            return tokenWithId;
        } catch (JWTVerificationException e) {
            e.printStackTrace();

            return "";
        }

    }
}
