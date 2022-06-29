package com.pagos.vault.mappers;

import com.pagos.vault.entities.*;
import com.pagos.vault.models.PaymentMethod;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class PaymentMethodEntityMapper {
    private AES256TextEncryptor encryptor;

    @Autowired
    public PaymentMethodEntityMapper() {
        this.encryptor = new AES256TextEncryptor();
        this.encryptor.setPassword("this_is_a_test");
    }

    public PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod) {
        PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();
        String fullDecryptedNumber = "";

        paymentMethodResponse.paymentMethodType = paymentMethod.paymentMethodType;
        paymentMethodResponse.id = paymentMethod.id;

        switch(paymentMethodResponse.paymentMethodType) {
            case "BankAccount":
                paymentMethodResponse.bankAccountResponse = new BankAccountResponse();
                paymentMethodResponse.bankAccountResponse.bankName = paymentMethod.bankAccount.bankName;
                paymentMethodResponse.bankAccountResponse.name = paymentMethod.bankAccount.name;
                paymentMethodResponse.bankAccountResponse.type = paymentMethod.bankAccount.type;
                paymentMethodResponse.bankAccountResponse.routing = paymentMethod.bankAccount.routing;
                fullDecryptedNumber = encryptor.decrypt(paymentMethod.bankAccount.encryptedData);
                paymentMethodResponse.bankAccountResponse.last4 = fullDecryptedNumber.substring(fullDecryptedNumber.length() - 4);

                break;

            default:
                paymentMethodResponse.creditCardResponse = new CreditCardResponse();
                paymentMethodResponse.creditCardResponse.expirationDate = paymentMethod.creditCard.expirationDate.toString();
                paymentMethodResponse.creditCardResponse.name = paymentMethod.creditCard.name;
                paymentMethodResponse.creditCardResponse.postalCode = paymentMethod.creditCard.postalCode;
                fullDecryptedNumber = encryptor.decrypt(paymentMethod.creditCard.encryptedData);
                paymentMethodResponse.creditCardResponse.last4 = fullDecryptedNumber.substring(fullDecryptedNumber.length() - 4);

                break;
        }

        return paymentMethodResponse;
    }

    public PaymentMethod toPaymentMethod(PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod paymentMethod = new PaymentMethod();

        paymentMethod.paymentMethodType = paymentMethodRequest.paymentMethodType;

        switch(paymentMethod.paymentMethodType) {
            case "BankAccount":
                paymentMethod.bankAccount = new BankAccount();
                paymentMethod.bankAccount.bankName = paymentMethodRequest.bankAccountRequest.bankName;
                paymentMethod.bankAccount.name = paymentMethodRequest.bankAccountRequest.name;
                paymentMethod.bankAccount.routing = paymentMethodRequest.bankAccountRequest.routing;
                paymentMethod.bankAccount.type = paymentMethodRequest.bankAccountRequest.type;
                paymentMethod.bankAccount.encryptedData = encryptor.encrypt(paymentMethodRequest.bankAccountRequest.account);
                break;

            default:
                paymentMethod.creditCard = new CreditCard();
                paymentMethod.creditCard.expirationDate = LocalDate.parse(paymentMethodRequest.creditCardRequest.expirationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                paymentMethod.creditCard.name = paymentMethodRequest.creditCardRequest.name;
                paymentMethod.creditCard.postalCode = paymentMethodRequest.creditCardRequest.postalCode;
                paymentMethod.creditCard.encryptedData = encryptor.encrypt(paymentMethodRequest.creditCardRequest.number);
                break;
        }

        return paymentMethod;
    }

    public PaymentMethodFullResponse toPaymentMethodFullResponse(PaymentMethod paymentMethod) {
        PaymentMethodFullResponse paymentMethodResponse = new PaymentMethodFullResponse();
        String fullDecryptedNumber = "";

        paymentMethodResponse.paymentMethodType = paymentMethod.paymentMethodType;
        paymentMethodResponse.id = paymentMethod.id;

        switch(paymentMethodResponse.paymentMethodType) {
            case "BankAccount":
                paymentMethodResponse.bankAccount = new BankAccountFullResponse();
                paymentMethodResponse.bankAccount.bankName = paymentMethod.bankAccount.bankName;
                paymentMethodResponse.bankAccount.name = paymentMethod.bankAccount.name;
                paymentMethodResponse.bankAccount.type = paymentMethod.bankAccount.type;
                paymentMethodResponse.bankAccount.routing = paymentMethod.bankAccount.routing;
                fullDecryptedNumber = encryptor.decrypt(paymentMethod.bankAccount.encryptedData);
                paymentMethodResponse.bankAccount.account = fullDecryptedNumber;

                break;

            default:
                paymentMethodResponse.creditCard = new CreditCardFullResponse();
                paymentMethodResponse.creditCard.expirationDate = paymentMethod.creditCard.expirationDate.toString();
                paymentMethodResponse.creditCard.name = paymentMethod.creditCard.name;
                paymentMethodResponse.creditCard.postalCode = paymentMethod.creditCard.postalCode;
                fullDecryptedNumber = encryptor.decrypt(paymentMethod.creditCard.encryptedData);
                paymentMethodResponse.creditCard.number = fullDecryptedNumber;

                break;
        }

        return paymentMethodResponse;
    }
}
