package org.reiikaw.moviesrest.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.reiikaw.moviesrest.exception.ServerLogicException;
import org.reiikaw.moviesrest.repository.BaseRepository;
import org.reiikaw.moviesrest.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
public class BaseServiceImpl<E,
        ID extends Serializable,
        ER extends BaseRepository<E, ID>> implements BaseService<E, ID> {

    protected final ER repository;

    public BaseServiceImpl(final ER repository) {
        this.repository = repository;
    }

    @Override
    public E save(final E entity) {
        return repository.save(entity);
    }

    @Override
    public E update(final E entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<E> findById(final ID id) {
        return repository.findById(id);
    }

    @Override
    public E deleteById(final ID id) {
        E entity = repository.findById(id).orElseThrow(() ->
                new ServerLogicException(
                        HttpStatus.NOT_FOUND,
                        "Сущность с идентификатором <%s> не найдена".formatted(id))
        );
        repository.deleteById(id);
        return entity;
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
