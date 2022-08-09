package com.ajru.pharmacy_product_system.business.model.exception;

import java.text.MessageFormat;

public class ProductAlreadyAssignedException extends RuntimeException{
    public ProductAlreadyAssignedException(final Long productId, final Long classificationId) {
        super(MessageFormat.format("Product: {0} is already assigned to classification: {1}", productId, classificationId));

    }
}
