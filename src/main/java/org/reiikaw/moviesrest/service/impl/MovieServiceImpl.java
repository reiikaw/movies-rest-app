package org.reiikaw.moviesrest.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.reiikaw.moviesrest.dto.MoviePatchRequest;
import org.reiikaw.moviesrest.dto.MovieProcessDto;
import org.reiikaw.moviesrest.entity.Movie;
import org.reiikaw.moviesrest.exception.ServerLogicException;
import org.reiikaw.moviesrest.mapper.MovieMapper;
import org.reiikaw.moviesrest.repository.MovieRepository;
import org.reiikaw.moviesrest.service.MovieService;
import org.reiikaw.moviesrest.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.UUID;
import java.util.function.UnaryOperator;

@Service
public class MovieServiceImpl extends BaseServiceImpl<Movie, MovieProcessDto, UUID, MovieRepository, MovieMapper> implements MovieService {

    private final MovieMapper mapper;
    private final MovieRepository repository;
    private final UserService userService;
    private final JsonMapper jsonMapper;

    public MovieServiceImpl(MovieRepository repository, MovieMapper mapper,
                            UserService userService, JsonMapper jsonMapper) {
        super(repository, mapper);
        this.mapper = mapper;
        this.repository = repository;
        this.userService = userService;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public Page<Movie> findAllAvailableMovies(Integer page, Integer size) {
        if (userService.getCurrentUser().getIsAdmin()) {
            return repository.findAll(PageRequest.of(page, size));
        } else {
            return repository.findAllByAvailable(true, PageRequest.of(page, size));
        }
    }

    @Override
    public Movie findAvailableMovieById(UUID movieId) {
        if (userService.getCurrentUser().getIsAdmin()) {
            return findById(movieId);
        } else {
            return repository.findByIdAndAvailable(true, movieId).orElseThrow(() ->
                    new ServerLogicException(
                            HttpStatus.NOT_FOUND,
                            "Сущность с идентификатором <%s> не найдена или недоступна".formatted(movieId))
            );
        }
    }

    @Override
    public Movie createMovie(MovieProcessDto createBody) {
        if (!userService.getCurrentUser().getIsAdmin()) {
            throw new ServerLogicException(
                    HttpStatus.FORBIDDEN,
                    "У вас нет прав на выполнение данной операции"
            );
        } else if (repository.existsByTitle(createBody.getTitle())) {
            throw new ServerLogicException(
                    HttpStatus.BAD_REQUEST,
                    "Фильм с названием <%s> уже существует".formatted(createBody.getTitle()));
        }

        Movie movieToCreate = mapper.toEntity(createBody);
        return save(movieToCreate);
    }

    @Override
    public Movie updateMovie(UUID id, MovieProcessDto updateBody) {
        if (!userService.getCurrentUser().getIsAdmin()) {
            throw new ServerLogicException(
                    HttpStatus.FORBIDDEN,
                    "У вас нет прав на выполнение данной операции"
            );
        }
        return super.update(id, updateBody);
    }

    @Override
    public Movie patchMovie(UUID id, MoviePatchRequest patchBody) {
        if (!userService.getCurrentUser().getIsAdmin()) {
            throw new ServerLogicException(
                    HttpStatus.FORBIDDEN,
                    "У вас нет прав на выполнение данной операции"
            );
        }

        Movie movieToUpdate = findById(id);
        JsonMapper patchMapper = jsonMapper.rebuild()
                .changeDefaultPropertyInclusion(oldValue ->
                        JsonInclude.Value.construct(
                                JsonInclude.Include.NON_NULL,
                                JsonInclude.Include.NON_NULL
                        )
                ).build();
        JsonNode movieNode = patchMapper.convertValue(movieToUpdate, JsonNode.class);
        JsonNode patchNode = patchMapper.convertValue(patchBody, JsonNode.class);

        if (patchNode.isObject() && movieNode.isObject()) {
            patchNode.properties().forEach(entry -> {
                String key = entry.getKey();
                JsonNode value = entry.getValue();

                if (value != null && !value.isNull()) {
                    ((ObjectNode) movieNode).set(key, value);
                }
            });
        }

        Movie patchedMovie = patchMapper.convertValue(movieNode, Movie.class);
        patchedMovie.setId(movieToUpdate.getId());
        return save(patchedMovie);
    }

    @Override
    public Movie deleteById(UUID id) {
        if (!userService.getCurrentUser().getIsAdmin()) {
            throw new ServerLogicException(
                    HttpStatus.FORBIDDEN,
                    "У вас нет прав на выполнение данной операции"
            );
        }
        return super.deleteById(id);
    }
}
