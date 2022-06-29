package com.pagos.identities.services;

import com.pagos.identities.entities.AccessToken;
import com.pagos.exceptions.BadRequestException;
import com.pagos.identities.models.Identity;
import com.pagos.identities.repositories.IdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pagos.auth.JWTBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class IdentityService {
    private IdentityRepository identityRepository;
    private JWTBuilder jwtBuilder;

    @Autowired
    public IdentityService(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
        this.jwtBuilder = new JWTBuilder();
    }

    public void validateRequest(Map<String, String> requestProperties) throws BadRequestException {
        if (!requestProperties.containsKey("GrantType")) throw new BadRequestException();

        switch(requestProperties.get("GrantType").toUpperCase()) {
            case "PASSWORD":
                if (!requestProperties.containsKey("ClientId")) throw new BadRequestException();
                if (!requestProperties.containsKey("ClientSecret")) throw new BadRequestException();
                break;

            default:
                throw new BadRequestException();
        }
    }

    public AccessToken createToken(Map<String, String> requestProperties) throws BadRequestException {
        Identity foundIdentity =
                identityRepository.findIdentityByClientIdAndClientSecret(requestProperties.get("ClientId"), requestProperties.get("ClientSecret"));

        if (foundIdentity == null) throw new BadRequestException();

        Map<String, Object> claims = new HashMap<>();
        claims.put("AuthorizationLevel", foundIdentity.authorizationLevel);
        claims.put("ClientId", foundIdentity.clientId.toString());
        claims.put("MerchantId", foundIdentity.merchantId);

        AccessToken accessToken = new AccessToken();
        accessToken.accessToken = jwtBuilder.build(claims);

        return accessToken;
    }
}
