package com.pagos.vault.controllers;

import com.pagos.auth.AuthorizationLevel;
import com.pagos.auth.JWTValidator;
import com.pagos.exceptions.UnauthorizedException;
import com.pagos.vault.entities.PaymentMethodFullResponse;
import com.pagos.vault.entities.PaymentMethodRequest;
import com.pagos.vault.entities.PaymentMethodResponse;
import com.pagos.vault.services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/paymentMethods")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;
    private final JWTValidator jwtValidator;

    @Autowired
    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.jwtValidator = new JWTValidator();
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    public List<PaymentMethodResponse> getPaymentMethods(@RequestHeader("Authorization") String authorization) throws UnauthorizedException {
        if (!jwtValidator.authorized(authorization, AuthorizationLevel.USER)) {
            throw new UnauthorizedException();
        }

        return paymentMethodService.findAll();
    }

    @GetMapping("/{id}")
    public PaymentMethodResponse getPaymentMethod(@RequestHeader("Authorization") String authorization,
                                                  @PathVariable("id") String id) throws UnauthorizedException {
        if (!jwtValidator.authorized(authorization, AuthorizationLevel.USER)) {
            throw new UnauthorizedException();
        }

        return paymentMethodService.find(id);
    }

    @GetMapping("/{id}/admin")
    public PaymentMethodFullResponse getAdminPaymentMethod(@RequestHeader("Authorization") String authorization,
                                                           @PathVariable("id") String id) throws UnauthorizedException {
        if (!jwtValidator.authorized(authorization, AuthorizationLevel.ADMIN)) {
            throw new UnauthorizedException();
        }

        return paymentMethodService.findFull(id);
    }


    @PostMapping
    public PaymentMethodResponse createPaymentMethod(@RequestHeader("Authorization") String authorization,
                                                     @RequestBody PaymentMethodRequest paymentMethodRequest) throws UnauthorizedException {
        if (!jwtValidator.authorized(authorization, AuthorizationLevel.USER)) {
            throw new UnauthorizedException();
        }
        return paymentMethodService.save(paymentMethodRequest);
    }
}
