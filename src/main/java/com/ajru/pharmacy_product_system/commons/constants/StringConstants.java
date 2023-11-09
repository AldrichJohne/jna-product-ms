package com.ajru.pharmacy_product_system.commons.constants;

public enum StringConstants {

    WEB_REQ("Web Request: Endpoint Description = %s, Method = %s, URL = %s"),
    WEB_RESP("Sending Response: %s"),
    SERVICE_LAYER("Service Layer: ClassName: {}, MethodName: {}, Description: {}"),
    SERVICE_LAYER_DESCRIPTION("Description: {}, MethodName: {}"),
    SERVICE_LAYER_UPDATE_FROM_TO("Updating {} from {} to {}, MethodName: {}");

    private final String value;

    StringConstants(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
