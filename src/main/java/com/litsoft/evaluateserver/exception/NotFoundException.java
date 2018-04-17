package com.litsoft.evaluateserver.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by xinghao1 on 2017/6/14.
 */
public class NotFoundException extends RuntimeException {
    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
