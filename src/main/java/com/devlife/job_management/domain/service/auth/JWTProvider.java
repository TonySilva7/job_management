package com.devlife.job_management.domain.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {

    @Value("${security.token.secret.candidate}")
    private String secretKeyCandidate;

    @Value("${security.token.secret.company}")
    private String secretKeyCompany;

    public String validateToken(String token) {

        token = token.replace("Bearer ", "");
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.decode(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();

            return "";
        }

        String role = decodedJWT.getClaim("role").asString();
        Algorithm algorithm;

        if (role.equals("CANDIDATE")) {
            algorithm = Algorithm.HMAC256(secretKeyCandidate);
        } else if(role.equals("COMPANY")) {
            algorithm = Algorithm.HMAC256(secretKeyCompany);
        } else {
            return "";
        }

        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            e.printStackTrace();

            return "";
        }

    }
}
