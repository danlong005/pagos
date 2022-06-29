package com.pagos.vault.services;

import com.pagos.vault.entities.PaymentMethodFullResponse;
import com.pagos.vault.entities.PaymentMethodRequest;
import com.pagos.vault.entities.PaymentMethodResponse;
import com.pagos.vault.mappers.PaymentMethodEntityMapper;
import com.pagos.vault.models.PaymentMethod;
import com.pagos.vault.repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodEntityMapper paymentMethodEntityMapper;

    @Autowired
    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository, PaymentMethodEntityMapper paymentMethodEntityMapper) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodEntityMapper = paymentMethodEntityMapper;
    }

    public List<PaymentMethodResponse> findAll() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        return paymentMethods.stream().map(paymentMethodEntityMapper::toPaymentMethodResponse).collect(Collectors.toList());
    }

    public PaymentMethodResponse find(String id) {
        return paymentMethodEntityMapper.toPaymentMethodResponse(paymentMethodRepository.findById(id).orElse(null));
    }

    public PaymentMethodFullResponse findFull(String id) {
        return paymentMethodEntityMapper.toPaymentMethodFullResponse(paymentMethodRepository.findById(id).orElse(null));
    }

    public PaymentMethodResponse save(PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod paymentMethod = paymentMethodEntityMapper.toPaymentMethod(paymentMethodRequest);
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return paymentMethodEntityMapper.toPaymentMethodResponse(paymentMethod);
    }
}
