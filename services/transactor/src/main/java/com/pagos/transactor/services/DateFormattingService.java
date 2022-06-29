package com.pagos.transactor.services;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateFormattingService {
    public String formatExpirationDate(String expirationDate) {
        LocalDate expDate = LocalDate.parse(expirationDate);
        return String.format("%02d", expDate.getMonthValue()) + String.valueOf(expDate.getYear()).substring(2);
    }
}
