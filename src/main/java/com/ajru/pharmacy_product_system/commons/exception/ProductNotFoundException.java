package com.ajru.pharmacy_product_system.commons.exception;

import java.text.MessageFormat;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(final Long id) {
        super(MessageFormat.format("Could not find product with id: {0}", id));
    }
}
