package com.pagos.orchestrator.entities;

public class Transaction {
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
}
