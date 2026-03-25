package org.reiikaw.moviesrest.controller.contract;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.reiikaw.moviesrest.dto.MoviePatchRequest;
import org.reiikaw.moviesrest.dto.MovieProcessDto;
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
public interface MovieControllerApi {

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectResponse> getMovieById(@PathVariable(name = "id", required = true) @NotNull UUID id);

    @RequestMapping(
            path = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectListResponse> getAllMovies(@RequestParam(name = "page", defaultValue = "0") @Min(value = 0, message = "Номер страницы должен быть больше или равен 0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "20") @Min(value = 1, message = "Размер страницы должен быть больше или равен 1") Integer size);

    @RequestMapping(
            path = "",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectResponse> createMovie(@RequestBody(required = true) @Valid @NotNull MovieProcessDto createBody);

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectResponse> fullUpdateMovie(@PathVariable(name = "id", required = true) @NotNull UUID id,
                                                   @RequestBody(required = true) @Valid @NotNull MovieProcessDto updateBody);

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectResponse> partialUpdateMovie(@PathVariable(name = "id", required = true) UUID id,
                                                      @RequestBody(required = true) @Valid @NotNull MoviePatchRequest updateBody);

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ObjectResponse> deleteMovieById(@PathVariable(name = "id", required = true) @NotNull UUID id);
}
