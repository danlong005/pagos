package com.pagos.vault.entities;

public class PaymentMethodFullResponse {
    public String id;
    public String paymentMethodType;
    public CreditCardFullResponse creditCard;
    public BankAccountFullResponse bankAccount;
}
