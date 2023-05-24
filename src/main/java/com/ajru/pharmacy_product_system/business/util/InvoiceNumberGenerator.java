package com.ajru.pharmacy_product_system.business.util;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class InvoiceNumberGenerator {

    public String invoiceNumber() {
        return getCurrentDateAndTime() + generateRandomAlphanumeric();
    }

    private static String generateRandomAlphanumeric() {
        int length = 3;
        byte[] randomBytes = new byte[length];
        new SecureRandom().nextBytes(randomBytes);
        String base64Encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        return base64Encoded.replaceAll("[^A-Za-z0-9]", "");
    }

    private static String getCurrentDateAndTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return currentDateTime.format(formatter);
    }
}
