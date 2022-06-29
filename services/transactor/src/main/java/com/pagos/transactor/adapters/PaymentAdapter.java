package com.pagos.transactor.adapters;

import com.pagos.transactor.models.Transaction;

public interface PaymentAdapter {
    Transaction authorization(Transaction transaction);
    Transaction refund(Transaction transaction);
    Transaction capture(Transaction transaction);
    Transaction verify(Transaction transaction);
}
