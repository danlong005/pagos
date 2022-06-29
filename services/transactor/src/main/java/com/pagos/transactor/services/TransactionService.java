package com.pagos.transactor.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagos.auth.JWTValidator;
import com.pagos.exceptions.PaymentAdapterNotFoundException;
import com.pagos.sdk.OrchestratorSdk;
import com.pagos.transactor.adapters.PaymentAdapter;
import com.pagos.transactor.models.Transaction;
import com.pagos.transactor.repositories.TransactionRepository;
import com.pagos.transactor.resolvers.PaymentAdapterResolver;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private PaymentAdapterResolver paymentAdapterResolver;
    private PaymentMethodService paymentMethodService;
    private JWTValidator jwtValidator;
    private OrchestratorSdk orchestratorSdk;

    private PaymentAdapter paymentAdapter;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              PaymentAdapterResolver paymentAdapterResolver,
                              PaymentMethodService paymentMethodService) {
        this.transactionRepository = transactionRepository;
        this.paymentAdapterResolver = paymentAdapterResolver;
        this.paymentMethodService = paymentMethodService;
        this.jwtValidator = new JWTValidator();
        this.orchestratorSdk = new OrchestratorSdk("dev");
    }

    public Transaction authorization(String authorization, Transaction transaction) throws PaymentAdapterNotFoundException, JsonProcessingException {
        transaction.attempt = 0;
        String updatedTransaction = orchestratorSdk.getVendor(new ObjectMapper().writeValueAsString(transaction));
        transaction = new ObjectMapper().readValue(updatedTransaction, Transaction.class);

        while (!StringUtils.equals(transaction.vendor, "GIVE_UP")) {
            paymentAdapter = paymentAdapterResolver.getService(transaction.vendor);

            transaction.paymentMethod = paymentMethodService.find(transaction.paymentMethodId);
            transaction.merchantId = jwtValidator.getMerchantId(authorization);

            transaction.id = null;
            transaction = transactionRepository.save(transaction);
            transaction = paymentAdapter.authorization(transaction);
            transaction = transactionRepository.save(transaction);

            transaction.attempt += 1;
            updatedTransaction = orchestratorSdk.getVendor(new ObjectMapper().writeValueAsString(transaction));
            transaction = new ObjectMapper().readValue(updatedTransaction, Transaction.class);
        }

        transaction.vendor = null;
        transaction.paymentMethod = null;
        transaction.attempt = null;

        return transaction;
    }
}
