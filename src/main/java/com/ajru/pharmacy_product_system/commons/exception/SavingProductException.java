package com.ajru.pharmacy_product_system.commons.exception;

import java.text.MessageFormat;

public class SavingProductException extends RuntimeException{

    public SavingProductException() {
        super(MessageFormat.format("There is an error saving the product/s to the database{}",""));
    }
}
