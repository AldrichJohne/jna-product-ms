package com.ajru.pharmacy_product_system.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jna")
public record ConfigProps(String userName, String password, String role, String secret, int id) {
}
