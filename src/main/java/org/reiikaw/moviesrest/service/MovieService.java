package org.reiikaw.moviesrest.service;

import org.reiikaw.moviesrest.dto.MoviePatchRequest;
import org.reiikaw.moviesrest.dto.MovieProcessDto;
import org.reiikaw.moviesrest.entity.Movie;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface MovieService extends BaseService<Movie, UUID, MovieProcessDto> {

    Movie createMovie(MovieProcessDto createBody);
    Movie findAvailableMovieById(UUID movieId);
    Page<Movie> findAllAvailableMovies(Integer page, Integer size);
    Movie updateMovie(UUID id, MovieProcessDto updateBody);
    Movie patchMovie(UUID id, MoviePatchRequest patchBody);
}
