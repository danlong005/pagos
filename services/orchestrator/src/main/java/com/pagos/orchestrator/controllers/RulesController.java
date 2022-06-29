package com.pagos.orchestrator.controllers;

import com.pagos.orchestrator.entities.Transaction;
import com.pagos.orchestrator.services.RulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RulesController {
    private RulesService rulesService;

    @Autowired
    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @PostMapping("/v1/rules")
    public Transaction getRule(@RequestBody Transaction transaction) {
        Transaction getRuleTransaction = new Transaction();

        getRuleTransaction = rulesService.getRule(getRuleTransaction);

        transaction.vendor = getRuleTransaction.vendor;

        return transaction;
    }
}
