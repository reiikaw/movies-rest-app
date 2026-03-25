package org.reiikaw.moviesrest.controller;

import lombok.RequiredArgsConstructor;
import org.reiikaw.moviesrest.controller.contract.MovieControllerApi;
import org.reiikaw.moviesrest.dto.MoviePatchRequest;
import org.reiikaw.moviesrest.dto.MovieProcessDto;
import org.reiikaw.moviesrest.dto.response.ObjectListResponse;
import org.reiikaw.moviesrest.dto.response.ObjectResponse;
import org.reiikaw.moviesrest.dto.response.PageInfo;
import org.reiikaw.moviesrest.entity.Movie;
import org.reiikaw.moviesrest.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MovieController implements MovieControllerApi {

    private final MovieService movieService;

    @Override
    public ResponseEntity<ObjectResponse> getMovieById(UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(
                "Сущность с идентификатором <%s> найдена успешно".formatted(id),
                HttpStatus.OK.toString(),
                movieService.findAvailableMovieById(id)
        ));
    }

    @Override
    public ResponseEntity<ObjectListResponse> getAllMovies(Integer page, Integer size) {
        Page<Movie> objectsPage = movieService.findAllAvailableMovies(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectListResponse<>(
                "Список сущностей: всего найдено %s".formatted(objectsPage.getTotalElements()),
                HttpStatus.OK.toString(),
                objectsPage.getContent(),
                new PageInfo(
                        objectsPage.getSize(),
                        objectsPage.getNumber(),
                        objectsPage.getTotalElements()
                )
        ));
    }

    @Override
    public ResponseEntity<ObjectResponse> createMovie(MovieProcessDto createBody) {
        Movie createdMovie = movieService.createMovie(createBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ObjectResponse(
                "Создана успешно сущность с идентификатором <%s>".formatted(createdMovie.getId()),
                HttpStatus.CREATED.toString(),
                createdMovie
        ));
    }

    @Override
    public ResponseEntity<ObjectResponse> fullUpdateMovie(UUID id, MovieProcessDto updateBody) {
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(
                "Сущность с идентификатором <%s> была успешно полностью обновлена".formatted(id),
                HttpStatus.OK.toString(),
                movieService.updateMovie(id, updateBody)
        ));
    }

    @Override
    public ResponseEntity<ObjectResponse> partialUpdateMovie(UUID id, MoviePatchRequest updateBody) {
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(
                "Сущность с идентификатором <%s> была успешно частично обновлена".formatted(id),
                HttpStatus.OK.toString(),
                movieService.patchMovie(id, updateBody)
        ));
    }

    @Override
    public ResponseEntity<ObjectResponse> deleteMovieById(UUID id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ObjectResponse(
                "Сущность с идентификатором <%s> была успешно удалена".formatted(id),
                HttpStatus.NO_CONTENT.toString(),
                movieService.deleteById(id)
        ));
    }
}
