package com.pixelpunch.passflow.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleInternalServerError(Exception ex) {
        log.error("Internal Server Error", ex);
        return new ErrorResponse("Internal Server Error");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HttpClientErrorException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            IllegalArgumentException.class,
            BadRequestException.class
    })
    public ErrorResponse handleBadRequestException(Exception ex) {
        log.error("Bad Request", ex);
        return new ErrorResponse("Bad Request");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponse handleMethodNotAllowed(Exception ex) {
        log.error("Method Not Allowed", ex);
        return new ErrorResponse("Method Not Allowed");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NoHandlerFoundException.class,
            EntityNotFoundException.class
    })
    public ErrorResponse handleNotFoundException(Exception ex) {
        log.error("Not Found", ex);
        return new ErrorResponse("Not Found");
    }
}
