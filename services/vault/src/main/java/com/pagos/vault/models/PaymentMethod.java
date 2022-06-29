package com.pagos.vault.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pagos.vault.entities.BankAccount;
import com.pagos.vault.entities.CreditCard;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("payment_methods")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentMethod {
    @Id
    public String id;
    public String paymentMethodType;
    public CreditCard creditCard;
    public BankAccount bankAccount;
}
