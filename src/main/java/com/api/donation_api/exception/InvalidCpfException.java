package com.api.donation_api.exception;

public class InvalidCpfException extends RuntimeException{
    public InvalidCpfException(String message){
        super(message);
    }
}
