package com.example.common.exception;


import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String missingElement;
    private final String message;

    public NotFoundException(Class<?> resourceClass) {
        this.missingElement = resourceClass.getSimpleName();
        this.message = null;
    }


    public NotFoundException(String missingElement) {
        this.missingElement = missingElement;
        this.message = null;
    }

}
