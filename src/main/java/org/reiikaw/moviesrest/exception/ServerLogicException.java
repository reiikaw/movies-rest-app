package org.reiikaw.moviesrest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServerLogicException extends RuntimeException {

    private final HttpStatus statusCode;
    private final String message;
    private final String exceptionInitiatorClassName;

    public ServerLogicException(HttpStatus statusCode, String message, String exceptionInitiatorClassName) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
        this.exceptionInitiatorClassName = exceptionInitiatorClassName;
    }

    public ServerLogicException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
        this.exceptionInitiatorClassName = ServerLogicException.class.getName();
    }
}
