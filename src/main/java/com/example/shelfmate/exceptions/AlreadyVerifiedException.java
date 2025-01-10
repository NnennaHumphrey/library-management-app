package com.example.shelfmate.exceptions;

public class AlreadyVerifiedException extends RuntimeException{
    public AlreadyVerifiedException(String message) {
        super(message);
    }
}
