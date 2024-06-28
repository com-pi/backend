package com.example.common.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;


@Getter
public class CommonException extends RuntimeException {

    @NonNull
    private final int httpStatus;

    // Todo HttpStatus를 받아서 처리할 수 있도록 수정
    public CommonException(String message, int httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public CommonException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
