package org.reiikaw.moviesrest.controller.advice;

import org.reiikaw.moviesrest.dto.response.ErrorResponse;
import org.reiikaw.moviesrest.exception.ServerLogicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import tools.jackson.databind.exc.InvalidFormatException;
import tools.jackson.databind.exc.MismatchedInputException;

import java.util.ArrayList;
import java.util.List;
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

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, String clazzName) {
        return ResponseEntity.status(status).body(
                new ErrorResponse(
                        message,
                        status.toString(),
                        clazzName
                )
        );
    }

    private ResponseEntity<ErrorResponse> buildValidationErrorResponse(BindingResult bindingResult, Exception e) {
        String errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(". "));

        log.error("Ошибка валидации: {}", errorMessages, e);
        return buildErrorResponse(
                HttpStatus.UNPROCESSABLE_CONTENT,
                errorMessages,
                e.getClass().getSimpleName()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        Throwable cause = getRootCauseMessage(e);

        return switch (cause) {
            case ServerLogicException ex -> {
                log.error("Произошла ошибка на стороне сервера. {}", ex.getMessage(), ex);
                yield buildErrorResponse(
                        ex.getStatusCode(),
                        ex.getMessage(),
                        ex.getExceptionInitiatorClassName()
                );
            }
            case MethodArgumentNotValidException ex -> buildValidationErrorResponse(ex.getBindingResult(), ex);
            case InvalidFormatException ex -> {
                log.error("Ошибка корректности входных параметров запроса: {}", ex.getMessage(), ex);
                yield buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        "Неверный формат данных для поля <%s>. Ожидаемый тип: %s"
                                .formatted(ex.getPath().getFirst().getPropertyName(), ex.getTargetType().getSimpleName()),
                        cause.getClass().getSimpleName()
                );
            }
            case MismatchedInputException ex -> {
                log.error("Ошибка корректности входных параметров запроса: {}", ex.getMessage(), ex);
                yield buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        "Неверный формат данных для поля <%s>. Ожидаемый тип: %s"
                                .formatted(ex.getPath().getFirst().getPropertyName(), ex.getTargetType().getSimpleName()),
                        cause.getClass().getSimpleName()
                );
            }
            case HandlerMethodValidationException ex -> {
                List<String> errorMessages = new ArrayList<>();
                ex.getParameterValidationResults().forEach(res -> {
                    errorMessages.add(res.getResolvableErrors().stream()
                            .map(MessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.joining(". ")));
                });

                log.error("Ошибка валидации: {}", errorMessages, ex);
                yield buildErrorResponse(
                        HttpStatus.UNPROCESSABLE_CONTENT,
                        "Ошибка валидации: " + errorMessages,
                        cause.getClass().getSimpleName()
                );
            }
            case HttpMessageNotReadableException ex -> {
                log.error("HttpMessageNotReadableException occurred: {}", ex.getMessage(), ex);
                yield buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        "Отсутствует тело запроса",
                        cause.getClass().getSimpleName()
                );
            }
            case HttpRequestMethodNotSupportedException ex -> {
                log.error("Не найдено обработчика для ресурса с методом {}: {}", ex.getMethod(), ex.getMessage(), ex);
                yield buildErrorResponse(
                        HttpStatus.METHOD_NOT_ALLOWED,
                        "Метод %s не поддерживается для этого ресурса".formatted(ex.getMethod()),
                        cause.getClass().getSimpleName()
                );
            }
            case NoResourceFoundException ex -> {
                log.error("Не найден ресурс: {}", ex.getMessage(), ex);
                yield buildErrorResponse(
                        HttpStatus.NOT_FOUND,
                        "Ресурс <%s> не найден".formatted(ex.getResourcePath()),
                        cause.getClass().getSimpleName()
                );
            }
            case BadCredentialsException ex -> {
                log.error("Неверные данные авторизации: {}", ex.getMessage(), ex);
                yield buildErrorResponse(
                        HttpStatus.UNAUTHORIZED,
                        "Введено неверное имя пользователя или пароль",
                        cause.getClass().getSimpleName()
                );
            }
            case AccessDeniedException ex -> {
                log.error("Нет доступа к ресурсу: {}", ex.getMessage(), ex);
                yield buildErrorResponse(
                        HttpStatus.FORBIDDEN,
                        "У вас нет доступа к этому ресурсу",
                        cause.getClass().getSimpleName()
                );
            }
            default -> {
                log.error("Произошла ошибка на стороне сервера: {}", cause.getMessage(), cause);
                yield buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        cause.getMessage(),
                        cause.getClass().getSimpleName()
                );
            }
        };
    }
}
