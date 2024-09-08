package com.dlong.api.controllers.v1;

import org.springframework.web.bind.annotation.RestController;

import com.dlong.api.models.PaymentMethod;
import com.dlong.api.repositories.PaymentMethodRepository;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class PaymentMethodController {
    
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodController(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @GetMapping("/v1/owner/{id}/payment_methods")
    public List<PaymentMethod> getPaymentMethodsByOwner(@PathVariable String id) {
        return paymentMethodRepository.findAllByOwner(id);
    }
    

    @PostMapping(value = "/v1/owner/{id}/payment_methods", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentMethod createPaymentMethodForOwner(@PathVariable String id, @RequestBody PaymentMethod paymentMethod) {
        paymentMethod.owner = id;
        return paymentMethodRepository.save(paymentMethod);
    }
    
}
