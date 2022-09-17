package com.postingBoard.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            AccessDeniedException.class,
            ConstraintViolationException.class,
            IncorrectInputException.class,
            JwtAuthenticationException.class,
            NullPointerException.class
    })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        if (ex instanceof IllegalArgumentException) {
            String bodyOfResponse = "wrong argument";
            logger.debug("IllegalArgumentException");
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.CONFLICT, request);

        }
        if (ex instanceof IllegalStateException) {
            String bodyOfResponse = "check log something wrong";
            logger.debug("IllegalStateException");
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.CONFLICT, request);
        }
        if (ex instanceof AccessDeniedException) {
            String bodyOfResponse = " not enough access rights";
            logger.debug("AccessDeniedException");
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.FORBIDDEN, request);
        }
        if (ex instanceof ConstraintViolationException) {
            String bodyOfResponse = " wrong input, probably username or login";
            logger.debug("ConstraintViolationException");

            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.CONFLICT, request);
        }
        if (ex instanceof IncorrectInputException) {
            String bodyOfResponse = " wrong input";
            logger.debug("IncorrectInputException");
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }
        if (ex instanceof JwtAuthenticationException) {
            String bodyOfResponse = " wrong username or password";
            logger.debug("JwtAuthenticationException");

            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.FORBIDDEN, request);
        }
        if (ex instanceof NullPointerException) {
            String bodyOfResponse = "cant find resource";
            logger.debug("NullPointerException");
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.NOT_FOUND, request);
        }
        if (ex instanceof DataIntegrityViolationException) {
            String bodyOfResponse = "wrong input change username or email";
            logger.debug("NullPointerException");
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.NOT_FOUND, request);
        }
        return null;
    }
}