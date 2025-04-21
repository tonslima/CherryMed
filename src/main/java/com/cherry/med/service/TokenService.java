package com.cherry.med.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cherry.med.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("cherrymed-api")                           // Insert token issuer (emissor) into the token
                    .withSubject(user.getLogin())                         // Insert user login into the token
                    .withClaim("role", user.getRole().name())      // Insert user id into the token
                    .withExpiresAt(genExpireDate())                     // Set token expiry date
                    .sign(algorithm);                                  // sign token using the initial algorithm
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generate token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("cherrymed-api")        // get issuer (emissor)
                    .build()                            // rebuild token
                    .verify(token)                      // validate token
                    .getSubject();                      // get login
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    public Instant genExpireDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
