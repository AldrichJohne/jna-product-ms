package com.ajru.pharmacy_product_system.business.model.exception;

import java.text.MessageFormat;

public class ClassificationNotFoundException extends RuntimeException{

    public ClassificationNotFoundException(final Long id) {
        super(MessageFormat.format("Could not find class with id: {0}", id));
    }
}
