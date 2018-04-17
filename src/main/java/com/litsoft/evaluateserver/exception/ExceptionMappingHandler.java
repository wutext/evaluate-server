package com.litsoft.evaluateserver.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionMappingHandler extends ResponseEntityExceptionHandler{

    protected final Log logger = LogFactory.getLog(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        logError(exception);
        return new ResponseEntity<>("Internal Server Error",
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> exceptionHandler(NotFoundException exception) {
        return new ResponseEntity(exception.getMessage(), exception.getHttpStatus());
    }

    private void logError(Exception exception) {
        String fullStackTrace = ExceptionUtils.getStackTrace(exception);
        logger.error("ResponseException exception: " + fullStackTrace);
    }
}
