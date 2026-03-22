package org.reiikaw.moviesrest.service;

import org.reiikaw.moviesrest.dto.MovieProcessDto;
import org.reiikaw.moviesrest.entity.Movie;

import java.util.UUID;

public interface MovieService extends BaseService<Movie, UUID, MovieProcessDto> {

    Movie createMovie(MovieProcessDto createBody);
}
