package com.api.donation_api.exception;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(String message){ super(message); }
}
