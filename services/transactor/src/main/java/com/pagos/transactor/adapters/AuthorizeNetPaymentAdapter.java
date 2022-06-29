package com.pagos.transactor.adapters;

import com.pagos.transactor.models.Transaction;
import com.pagos.transactor.services.DateFormattingService;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class AuthorizeNetPaymentAdapter implements PaymentAdapter {

    // @Value("${com.pagos.authorize_net.apiLoginId}")
    private String apiLoginId = "77RSr6MEjfmy";

    // @Value("${com.pagos.authorize_net.transactionKey{")
    private String transactionKey = "5Z7TZvzG6C588Nd3";

    private DateFormattingService dateFormattingService;

    public AuthorizeNetPaymentAdapter(DateFormattingService dateFormattingService) {
        this.dateFormattingService = dateFormattingService;
    }

    @Override
    public Transaction authorization(Transaction transaction) {
        // Set the request to operate in either the sandbox or production environment
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        // Create object with merchant authentication details
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);

        // Populate the payment data
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(transaction.paymentMethod.creditCard.number);
        creditCard.setExpirationDate(dateFormattingService.formatExpirationDate(transaction.paymentMethod.creditCard.expirationDate));
        paymentType.setCreditCard(creditCard);

        // Create the payment transaction object
        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.AUTH_ONLY_TRANSACTION.value());
        txnRequest.setPayment(paymentType);
        txnRequest.setAmount(new BigDecimal(transaction.amount).setScale(2, RoundingMode.CEILING));

        // Create the API request and set the parameters for this specific request
        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setMerchantAuthentication(merchantAuthenticationType);
        apiRequest.setTransactionRequest(txnRequest);

        // Call the controller
        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();

        // Get the response
        CreateTransactionResponse response = new CreateTransactionResponse();
        response = controller.getApiResponse();
        TransactionResponse result = response.getTransactionResponse();

        transaction.response = result.getMessages().getMessage().get(0).getCode();
        transaction.message = result.getMessages().getMessage().get(0).getDescription();
        transaction.vendor = "AUTHORIZE_NET";
        transaction.vendorId = result.getTransId();

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
}
