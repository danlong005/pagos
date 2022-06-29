package com.pagos.transactor.resolvers;

import com.pagos.exceptions.PaymentAdapterNotFoundException;
import com.pagos.transactor.adapters.AuthorizeNetPaymentAdapter;
import com.pagos.transactor.adapters.PaymentAdapter;
import com.pagos.transactor.adapters.WorldPayPaymentAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentAdapterResolver {
    private WorldPayPaymentAdapter worldPayPaymentAdapter;
    private AuthorizeNetPaymentAdapter authorizeNetPaymentAdapter;

    private final String WORLD_PAY = "WORLD_PAY";
    private final String AUTHORIZE_NET = "AUTHORIZE_NET";

    @Autowired
    public PaymentAdapterResolver(WorldPayPaymentAdapter worldPayPaymentAdapter, AuthorizeNetPaymentAdapter authorizeNetPaymentAdapter) {
        this.authorizeNetPaymentAdapter = authorizeNetPaymentAdapter;
        this.worldPayPaymentAdapter = worldPayPaymentAdapter;
    }

    public PaymentAdapter getService(String vendor) throws PaymentAdapterNotFoundException {
        switch(vendor.toUpperCase()) {
            case WORLD_PAY:
                return worldPayPaymentAdapter;
            case AUTHORIZE_NET:
                return authorizeNetPaymentAdapter;
        }
        throw new PaymentAdapterNotFoundException();
    }
}
