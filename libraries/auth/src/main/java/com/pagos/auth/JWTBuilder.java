package com.pagos.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JWTBuilder {
    public String build(Map<String, Object> claims) {
        Date expirationDate = Date.from(Instant.now().plusSeconds(86400));
        return JWT.create()
                .withIssuer("com.pagos")
                .withPayload(claims)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256("com.pagos"));
    }
}
