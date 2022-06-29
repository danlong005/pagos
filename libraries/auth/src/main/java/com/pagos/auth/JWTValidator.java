package com.pagos.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTValidator {
    public Boolean verify(String jwt) {
        return (decode(jwt) != null);
    }

    public DecodedJWT decode(String jwt) {
        try {
            jwt = jwt.replace("Bearer ", "");
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("com.pagos"))
                    .withIssuer("com.pagos")
                    .build();
            return verifier.verify(jwt);
        } catch (JWTDecodeException exception) {
            return null;
        }
    }

    public Boolean authorized(String jwt, Integer level) {
        DecodedJWT decodedJWT = decode(jwt);
        if (decodedJWT == null) return false;

        Claim authorizationLevel = decodedJWT.getClaim("AuthorizationLevel");
        if (authorizationLevel == null) return false;

        return authorizationLevel.asInt() >= level;
    }

    public String getMerchantId(String jwt) {
        DecodedJWT decodedJWT = decode(jwt);
        if (decodedJWT == null) return null;

        Claim merchantId = decodedJWT.getClaim("MerchantId");
        return merchantId.asString();
    }
}