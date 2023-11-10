package com.ajru.pharmacy_product_system.business.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class InvoiceNumberGenerator {

    private static final String RANDOM_ALPHANUMERIC_REPLACEMENT = "RD26";
    private final Logger logger;

    public InvoiceNumberGenerator() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public final String invoiceNumber() {
        return getCurrentDateAndTime() + generateRandomAlphanumeric();
    }

    private String generateRandomAlphanumeric() {
        try {
            final int length = 3;
            final byte[] randomBytes = new byte[length];
            new SecureRandom().nextBytes(randomBytes);
            final String baseSixtyFourEncoded = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
            return baseSixtyFourEncoded.replaceAll("[^A-Za-z0-9]", "");
        } catch (final Exception err) {
            this.logger.info("An error occurred while generating random alphanumeric: {}", err.getMessage());
            return RANDOM_ALPHANUMERIC_REPLACEMENT;
        }
    }

    private static String getCurrentDateAndTime() {
        final LocalDateTime currentDateTime = LocalDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return currentDateTime.format(formatter);
    }
}
