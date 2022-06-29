package com.pagos.orchestrator.services;

import com.pagos.orchestrator.entities.Transaction;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RulesService {
    private KieContainer kieContainer;

    @Autowired
    public RulesService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public Transaction getRule(Transaction transaction) {

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(transaction);
        kieSession.fireAllRules();
        kieSession.dispose();

        return transaction;
    }
}
