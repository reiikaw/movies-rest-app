package org.reiikaw.moviesrest.controller.contract;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.reiikaw.moviesrest.dto.MoviePatchRequest;
import org.reiikaw.moviesrest.dto.MovieProcessDto;
import org.reiikaw.moviesrest.dto.response.ErrorResponse;
import org.reiikaw.moviesrest.dto.response.ObjectListResponse;
import org.reiikaw.moviesrest.dto.response.ObjectResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Validated
@RequestMapping("/api/v1/entities")
@SecurityRequirement(name = "Bearer Authentication")
public interface MovieControllerApi {

    @Operation(
            summary = "Получение доступного фильма по идентификатору",
            description = "Получение доступного фильма по идентификатору (available = true). Если пользователь является администратором, то он может получить недоступную сущность.",
            tags = {"movies"},
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
                            responseCode = "401",
                            description = "Ошибка авторизации",
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
                            responseCode = "404",
                            description = "Сущность не найдена",
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
            path = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectResponse> getMovieById(@PathVariable(name = "id", required = true) @NotNull UUID id);

    @Operation(
            summary = "Получение списка доступных фильмов",
            description = "Получение списка доступных фильмов (available = true). Если пользователь является администратором, то он получит и недоступные сущности.",
            tags = {"movies"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный ответ",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ObjectListResponse.class
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
                            responseCode = "401",
                            description = "Ошибка авторизации",
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
            path = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectListResponse> getAllMovies(@RequestParam(name = "page", defaultValue = "0") @Min(value = 0, message = "Номер страницы должен быть больше или равен 0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "20") @Min(value = 1, message = "Размер страницы должен быть больше или равен 1") Integer size);

    @Operation(
            summary = "Создание фильма",
            description = "Создание сущности фильма. Метод доступен только пользователю с правами администратора.",
            tags = {"movies"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Успешный ответ. Сущность создана",
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
                            responseCode = "401",
                            description = "Ошибка авторизации",
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
                            responseCode = "403",
                            description = "Доступ запрещен",
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
            path = "",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectResponse> createMovie(@RequestBody(required = true) @Valid @NotNull MovieProcessDto createBody);

    @Operation(
            summary = "Полное редактирование фильма по идентификатору",
            description = "Полное редактирование фильма по идентификатору. Метод доступен только пользователю с правами администратора.",
            tags = {"movies"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный ответ. Сущность отредактирована",
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
                            responseCode = "401",
                            description = "Ошибка авторизации",
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
                            responseCode = "403",
                            description = "Доступ запрещен",
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
                            responseCode = "404",
                            description = "Сущность не найдена",
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
            path = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectResponse> fullUpdateMovie(@PathVariable(name = "id", required = true) @NotNull UUID id,
                                                   @RequestBody(required = true) @Valid @NotNull MovieProcessDto updateBody);

    @Operation(
            summary = "Частичное редактирование фильма по идентификатору",
            description = "Частичное редактирование фильма по идентификатору. Метод доступен только пользователю с правами администратора.",
            tags = {"movies"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный ответ. Сущность отредактирована",
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
                            responseCode = "401",
                            description = "Ошибка авторизации",
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
                            responseCode = "403",
                            description = "Доступ запрещен",
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
                            responseCode = "404",
                            description = "Сущность не найдена",
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
            path = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectResponse> partialUpdateMovie(@PathVariable(name = "id", required = true) UUID id,
                                                      @RequestBody(required = true) @Valid @NotNull MoviePatchRequest updateBody);

    @Operation(
            summary = "Удаление фильма по идентификатору",
            description = "Удаление фильма по идентификатору. Метод доступен только пользователю с правами администратора.",
            tags = {"movies"},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Успешный ответ. Сущность удалена",
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
                            responseCode = "401",
                            description = "Ошибка авторизации",
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
                            responseCode = "403",
                            description = "Доступ запрещен",
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
                            responseCode = "404",
                            description = "Сущность не найдена",
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
            path = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectResponse> deleteMovieById(@PathVariable(name = "id", required = true) @NotNull UUID id);
}
