package com.pagos.transactor.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagos.sdk.IdentitiesSdk;
import com.pagos.sdk.VaultSdk;
import com.pagos.transactor.entities.Identity;
import com.pagos.transactor.entities.PaymentMethod;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService {
    private VaultSdk vaultSdk;
    private IdentitiesSdk identitiesSdk;

   // @Value("${com.pagos.environment}")
    private String environment = "dev";

    // @Value("${com.pagos.clientId")
    private String clientId = "ccf59cd7-866c-40e6-a730-4dfc60e13dd3";

    // @Value("${com.pagos.clientSecret}")
    private String clientSecret = "8f8aba60-3795-4c53-8404-4e2a4915c39a";

    public PaymentMethodService() {
        this.vaultSdk = new VaultSdk(environment);
        this.identitiesSdk = new IdentitiesSdk(environment);
    }

    public PaymentMethod find(String id) throws JsonProcessingException {
        String identityBody = identitiesSdk.getAccessToken("Password", clientId, clientSecret);
        Identity admin = new ObjectMapper().readValue(identityBody, Identity.class);
        String paymentMethodBody = vaultSdk.getPaymentMethod(admin.accessToken, id);

        return new ObjectMapper().readValue(paymentMethodBody, PaymentMethod.class);
    }
}
