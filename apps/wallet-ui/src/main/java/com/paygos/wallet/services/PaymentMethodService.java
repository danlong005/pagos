package com.paygos.wallet.services;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.paygos.wallet.models.PaymentMethod;

@Service
public class PaymentMethodService {
    private final WebClient webClient;
    private final String baseUrl = "http://localhost:8081/"; 

    public PaymentMethodService(RestClient.Builder restClientBuilder) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public List<PaymentMethod> getPaymentMethods(String id) {
        List<PaymentMethod> methods = webClient.get()
            .uri("/v1/owner/" + id + "/payment_methods")
            .retrieve().toEntityList(PaymentMethod.class).block().getBody();

        return methods;
    }

    public void createPaymentMethod(String id, PaymentMethod paymentMethod) {

        System.out.println(paymentMethod.toJSON());

        HttpStatusCode statusCode = webClient.post()
            .uri("/v1/owner/" + id + "/payment_methods")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(paymentMethod))
            .retrieve().toEntity(PaymentMethod.class).block().getStatusCode();
            
        if (statusCode.isError()) throw new RuntimeException(statusCode.toString());
    }
}
