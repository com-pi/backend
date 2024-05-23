package com.example.common.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;


@Getter
public class CommonException extends RuntimeException {

    @NonNull
    private final int httpStatus;

    public CommonException(String message, int httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public CommonException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
