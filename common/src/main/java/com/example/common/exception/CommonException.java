package com.example.common.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;


@Getter
public class CommonException extends RuntimeException {

    @NonNull
    private final int httpStatus;

    public CommonException(String message, @NonNull int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
