package com.example.boardservice.exception;

public class DuplicateLikeException extends RuntimeException {

    public DuplicateLikeException(String message) {
        super(message);
    }
}
