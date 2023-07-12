package com.ajru.pharmacy_product_system.business.util;

import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class InvoiceNumberGenerator {

    private final Logger logger;

    public InvoiceNumberGenerator() {
        this.logger = LoggerFactory.getLogger(this.getClass());;
    }

    public String invoiceNumber() {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "generating invoice number");
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
