package com.ajru.pharmacy_product_system.model.exception;

import com.ajru.pharmacy_product_system.model.Classification;

import java.text.MessageFormat;

public class ClassificationNotFoundException extends RuntimeException{

    public ClassificationNotFoundException(final Long id) {
        super(MessageFormat.format("Could not find class with id: {0}", id));
    }
}
