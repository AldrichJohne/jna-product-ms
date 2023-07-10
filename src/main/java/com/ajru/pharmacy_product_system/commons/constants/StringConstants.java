package com.ajru.pharmacy_product_system.commons.constants;

public enum StringConstants {

    WEB_REQ("Web request: Endpoint description = {}, Method = {}, URL = {}"),
    WEB_RESP("Sending response: {}");

    private final String value;

    StringConstants(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
