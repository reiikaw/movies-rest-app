package org.reiikaw.moviesrest.service.impl;

import org.reiikaw.moviesrest.dto.MovieProcessDto;
import org.reiikaw.moviesrest.entity.Movie;
import org.reiikaw.moviesrest.exception.ServerLogicException;
import org.reiikaw.moviesrest.mapper.MovieMapper;
import org.reiikaw.moviesrest.repository.MovieRepository;
import org.reiikaw.moviesrest.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MovieServiceImpl extends BaseServiceImpl<Movie, MovieProcessDto, UUID, MovieRepository, MovieMapper> implements MovieService {

    private final MovieMapper mapper;
    private final MovieRepository repository;

    public MovieServiceImpl(MovieRepository repository, MovieMapper mapper) {
        super(repository, mapper);
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Movie createMovie(MovieProcessDto createBody) {
        if (repository.existsByTitle(createBody.getTitle())) {
            throw new ServerLogicException(
                    HttpStatus.BAD_REQUEST,
                    "Фильм с названием <%s> уже существует".formatted(createBody.getTitle()));
        }

        Movie movieToCreate = mapper.toEntity(createBody);
        return save(movieToCreate);
    }
}
