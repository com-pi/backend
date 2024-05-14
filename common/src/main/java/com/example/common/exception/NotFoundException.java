package com.example.common.exception;


import lombok.Getter;

import java.util.Objects;

@Getter
public class NotFoundException extends RuntimeException {

    private final Class<?> resourceClass;
    private final String message;

    public NotFoundException(Class<?> resourceClass) {
        this.resourceClass = resourceClass;
        this.message = null;
    }

    public NotFoundException(String message) {
        resourceClass = null;
        this.message = message;
    }

    public String getMissingElement(){
        return Objects.requireNonNull(resourceClass).getSimpleName();
    }

}
