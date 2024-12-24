package com.api.donation_api.exception;

public class InvalidPostalCodeException extends RuntimeException{
    public InvalidPostalCodeException(String message){
        super(message);
    }
}
