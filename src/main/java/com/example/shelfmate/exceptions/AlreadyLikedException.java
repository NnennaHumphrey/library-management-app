package com.example.shelfmate.exceptions;

public class AlreadyLikedException extends RuntimeException{
    public AlreadyLikedException(String message) {
        super(message);
    }
}
