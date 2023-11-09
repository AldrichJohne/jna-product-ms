package com.ajru.pharmacy_product_system.commons.exception;

public class FetchingFromDatabaseException extends RuntimeException{
    public FetchingFromDatabaseException() {
        super("Error fetching from the database");
    }
}
