package com.pagos.transactor.adapters;

import com.pagos.transactor.models.Transaction;
import com.pagos.transactor.services.DateFormattingService;
import io.github.vantiv.sdk.CnpOnline;
import io.github.vantiv.sdk.generate.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class WorldPayPaymentAdapter implements PaymentAdapter {
    private CnpOnline cnpOnline;
    private DateFormattingService dateFormattingService;

    public WorldPayPaymentAdapter(DateFormattingService dateFormattingService) throws IOException {
        this.dateFormattingService = dateFormattingService;
        Resource resource = new ClassPathResource("/cnp_SDK_config.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);

        cnpOnline = new CnpOnline(props);
    }

    @Override
    public Transaction authorization(Transaction transaction) {
        Authorization authorization = new Authorization();
        authorization.setReportGroup(transaction.division);
        authorization.setOrderId(transaction.id);
        authorization.setId(transaction.id);
        authorization.setCustomerId(transaction.customerId);
        authorization.setAmount(transaction.amount.longValue());
        authorization.setOrderSource(OrderSourceType.ECOMMERCE);
        CardType card = new CardType();
        card.setNumber(transaction.paymentMethod.creditCard.number);
        card.setType(translateCardType(transaction.paymentMethod.creditCard.number));
        card.setExpDate(dateFormattingService.formatExpirationDate(transaction.paymentMethod.creditCard.expirationDate));
        card.setCardValidationNum("");
        authorization.setCard(card);

        AuthorizationResponse response = cnpOnline.authorize(authorization);

        transaction.vendor = "WORLD_PAY";
        transaction.vendorId = String.valueOf(response.getCnpTxnId());
        transaction.response = response.getResponse();
        transaction.message = response.getMessage();

        return transaction;
    }

    @Override
    public Transaction refund(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction capture(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction verify(Transaction transaction) {
        return null;
    }



    private MethodOfPaymentTypeEnum translateCardType(String cardNumber) {
        switch (cardNumber.substring(0,1)) {
            case "3":
                return MethodOfPaymentTypeEnum.AX;
            case "6":
                return MethodOfPaymentTypeEnum.DC;
            case "5":
                return MethodOfPaymentTypeEnum.MC;
            case "4":
                return MethodOfPaymentTypeEnum.VI;
            default:
                return MethodOfPaymentTypeEnum.BLANK;
        }
    }
}
