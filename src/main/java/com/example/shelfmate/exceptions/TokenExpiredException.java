package com.example.shelfmate.exceptions;

public class TokenExpiredException extends RuntimeException{

    public TokenExpiredException (String message) {
        super(message);
    }
}
