package com.nabo.buyer.exception;

public class CreateEntityException extends RuntimeException {
    public CreateEntityException() {
        super();
    }

    public CreateEntityException(String message) {
        super(message);
    }
}
