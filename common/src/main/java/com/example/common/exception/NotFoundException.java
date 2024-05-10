package com.example.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private final Class<?> resourceClass;
    private final String message;

    public NotFoundException(Class<?> resourceClass) {
        this.resourceClass = resourceClass;
        message = null;
    }

    public NotFoundException(String message, Class<?> resourceClass) {
        super(message);
        this.resourceClass = resourceClass;
        this.message = message;
    }

}
