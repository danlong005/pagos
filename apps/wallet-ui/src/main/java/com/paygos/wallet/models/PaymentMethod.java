package com.paygos.wallet.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaymentMethod extends BaseModel {
    public String id;
    public String owner;
    public String type;
    public String subType;
    public String last4;
}
