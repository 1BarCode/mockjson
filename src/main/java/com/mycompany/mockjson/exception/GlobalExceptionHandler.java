package com.mycompany.mockjson.exception;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class) // generic exception handler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleGenericException(HttpServletRequest request, Exception exception) {
        ErrorDTO error = new ErrorDTO();

        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.addError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        error.setPath(request.getServletPath());

        LOGGER.error(exception.getMessage(), exception);

        return error;
    }

    @ExceptionHandler(IdMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleIdMismatchException(HttpServletRequest request, IdMismatchException exception) {
        ErrorDTO error = new ErrorDTO();

        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.addError(exception.getMessage());
        error.setPath(request.getServletPath());

        LOGGER.error(exception.getMessage(), exception);

        return error;
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleDuplicateResourceException(HttpServletRequest request,
            DuplicateResourceException exception) {
        ErrorDTO error = new ErrorDTO();

        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.addError(exception.getMessage());
        error.setPath(request.getServletPath());

        LOGGER.error(exception.getMessage(), exception);

        return error;
    }

    // global validation exception handler
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDTO error = new ErrorDTO();

        LOGGER.error(ex.getMessage(), ex);

        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setPath(((ServletWebRequest) request).getRequest().getServletPath());

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.addError(fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(error, headers, status);
    }

}
