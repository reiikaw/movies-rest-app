package org.reiikaw.moviesrest.controller.advice;

import org.reiikaw.moviesrest.dto.ErrorResponse;
import org.reiikaw.moviesrest.exception.ServerLogicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private Throwable getRootCauseMessage(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if (cause != null) {
            return getRootCauseMessage(cause);
        }
        return throwable;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        Throwable cause = getRootCauseMessage(e);
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("Unhandled exception occurred: {}", cause.getMessage(), e);
        return ResponseEntity.status(status).body(
                new ErrorResponse(
                        cause.getMessage(),
                        status.toString(),
                        cause.getClass().getName()
                )
        );
    }

    @ExceptionHandler(ServerLogicException.class)
    public ResponseEntity<ErrorResponse> handleException(ServerLogicException e) {
        HttpStatusCode status = HttpStatusCode.valueOf(e.getStatusCode().value());
        log.error("Response error: {}", e.getMessage(), e);
        return ResponseEntity.status(status).body(
                new ErrorResponse(
                        e.getMessage(),
                        status.toString(),
                        e.getExceptionInitiatorClassName()
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" "));
        HttpStatusCode status = e.getStatusCode();
        log.error("Validation errors: {}", errorMessages, e);
        return ResponseEntity.status(status).body(
                new ErrorResponse(
                        errorMessages,
                        status.toString(),
                        e.getClass().getName()
                )
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException e) {
        Throwable cause = getRootCauseMessage(e);
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        log.error("HttpMessageNotReadableException occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(status).body(
                new ErrorResponse(
                        cause.getMessage(),
                        status.toString(),
                        cause.getClass().getName()
                )
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException e) {
        log.error("Bad credentials exception occurred: {}", e.getMessage(), e);
        HttpStatusCode status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(
                new ErrorResponse(
                        "Incorrect password entered",
                        status.toString(),
                        e.getClass().getName()
                )
        );
    }
}
