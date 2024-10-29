package com.api.donation_api.exception;

public class InvalidResourceException extends RuntimeException {
    public InvalidResourceException(String mensagem){ super(mensagem); }
}
