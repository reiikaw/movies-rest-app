package org.reiikaw.moviesrest.controller.contract;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.reiikaw.moviesrest.dto.MovieProcessDto;
import org.reiikaw.moviesrest.dto.response.ObjectListResponse;
import org.reiikaw.moviesrest.dto.response.ObjectResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RequestMapping("/api/v1/entities")
public interface MovieControllerApi {

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.GET
    )
    ResponseEntity<ObjectResponse> getMovieById(@PathVariable(name = "id", required = true) @NotNull UUID id);

    @RequestMapping(
            path = "",
            method = RequestMethod.GET
    )
    ResponseEntity<ObjectListResponse> getAllMovies(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "20") Integer size);

    @RequestMapping(
            path = "",
            method = RequestMethod.POST
    )
    ResponseEntity<ObjectResponse> createMovie(@RequestBody(required = true) @Valid @NotNull MovieProcessDto createBody);

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.PUT
    )
    ResponseEntity<ObjectResponse> fullUpdateMovie(@PathVariable(name = "id", required = true) @NotNull UUID id,
                                                   @RequestBody(required = true) @Valid @NotNull MovieProcessDto updateBody);

    @RequestMapping(
            path = "",
            method = RequestMethod.PATCH
    )
    ResponseEntity<ObjectResponse> partialUpdateMovie(@PathVariable(name = "id", required = true) UUID id,
                                                      @RequestBody(required = true) @Valid @NotNull MovieProcessDto updateBody);

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.DELETE
    )
    ResponseEntity<ObjectResponse> deleteMovieById(@PathVariable(name = "id", required = true) @NotNull UUID id);
}
