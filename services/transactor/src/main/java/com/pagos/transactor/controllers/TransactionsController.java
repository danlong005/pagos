package com.pagos.transactor.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pagos.auth.JWTValidator;
import com.pagos.exceptions.PaymentAdapterNotFoundException;
import com.pagos.transactor.models.Transaction;
import com.pagos.transactor.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionsController {
    private TransactionService transactionService;
    private JWTValidator jwtValidator;

    @Autowired
    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;

        this.jwtValidator = new JWTValidator();
    }

    @PostMapping(value="/authorization", consumes = "application/json")
    public Transaction authorization(@RequestBody Transaction transaction, @RequestHeader("Authorization") String authorization)
            throws PaymentAdapterNotFoundException, JsonProcessingException {
        authorization = jwtValidator.decode(authorization).getToken();
        return transactionService.authorization(authorization, transaction);
    }

    @PostMapping(value="/sale", consumes = "application/json")
    public Transaction sale(@RequestBody Transaction transaction) {
        return transaction;
    }

    @PostMapping(value="/verification", consumes = "application/json")
    public Transaction verification(@RequestBody Transaction transaction) {
        return transaction;
    }

    @PostMapping(value="/refund", consumes = "application/json")
    public Transaction refund(@RequestBody Transaction transaction) {
        return transaction;
    }
}
