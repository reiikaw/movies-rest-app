package org.reiikaw.moviesrest.controller.contract;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.reiikaw.moviesrest.dto.auth.AuthRequest;
import org.reiikaw.moviesrest.dto.response.ErrorResponse;
import org.reiikaw.moviesrest.dto.response.ObjectResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/api/v1/auth")
public interface AuthControllerApi {

    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Регистрация нового пользователя и получение токена доступа.",
            tags = {"authorization"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный ответ",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ObjectResponse.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ErrorResponse.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "405",
                            description = "Метод не доступен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ErrorResponse.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Ошибка валидации",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ErrorResponse.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ErrorResponse.class
                                            )
                                    )
                            }
                    )
            }
    )
    @RequestMapping(
            path = "/registration",
            method = RequestMethod.POST
    )
    ResponseEntity<ObjectResponse> signUp(@RequestBody @Valid AuthRequest request);

    @Operation(
            summary = "Авторизация существующего пользователя",
            description = "Авторизация существующего пользователя и получение токена доступа.",
            tags = {"authorization"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный ответ",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ObjectResponse.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ErrorResponse.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "405",
                            description = "Метод не доступен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ErrorResponse.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Ошибка валидации",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ErrorResponse.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ErrorResponse.class
                                            )
                                    )
                            }
                    )
            }
    )
    @RequestMapping(
            path = "/login",
            method = RequestMethod.POST
    )
    ResponseEntity<ObjectResponse> signIn(@RequestBody @Valid AuthRequest request);
}
