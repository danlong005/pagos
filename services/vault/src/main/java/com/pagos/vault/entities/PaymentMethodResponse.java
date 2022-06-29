package com.pagos.vault.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentMethodResponse {
    public String id;
    public String paymentMethodType;
    public CreditCardResponse creditCardResponse;
    public BankAccountResponse bankAccountResponse;
}
