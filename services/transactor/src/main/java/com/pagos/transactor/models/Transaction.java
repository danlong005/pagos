package com.pagos.transactor.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pagos.transactor.entities.PaymentMethod;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("transactions")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    @Id
    public String id;
    public Double amount;
    public String paymentMethodId;
    public String division;
    public String response;
    public String message;
    public String vendor;
    public String vendorId;
    public String customerId;
    public String merchantId;
    public Integer attempt = 0;

    @Transient
    public PaymentMethod paymentMethod;
}
