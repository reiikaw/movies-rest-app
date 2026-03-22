package org.reiikaw.moviesrest.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.reiikaw.moviesrest.exception.ServerLogicException;
import org.reiikaw.moviesrest.mapper.BaseMapper;
import org.reiikaw.moviesrest.repository.BaseRepository;
import org.reiikaw.moviesrest.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Slf4j
public class BaseServiceImpl<E, D,
        ID extends Serializable,
        ER extends BaseRepository<E, ID>,
        EM extends BaseMapper<E, D>> implements BaseService<E, ID, D> {

    protected final ER repository;
    protected final EM mapper;

    public BaseServiceImpl(final ER repository, final EM mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public E save(final E entity) {
        return repository.save(entity);
    }

    @Override
    public E update(final ID id, final D updateBody) {
        E entityToUpdate = findById(id);
        E updatedEntity = mapper.updateEntityFromDto(entityToUpdate, updateBody);
        return repository.save(updatedEntity);
    }

    @Override
    public E findById(final ID id) {
        return repository.findById(id).orElseThrow(() ->
                new ServerLogicException(
                        HttpStatus.NOT_FOUND,
                        "Сущность с идентификатором <%s> не найдена".formatted(id))
        );
    }

    @Override
    public E deleteById(final ID id) {
        E entity = findById(id);
        repository.deleteById(id);
        return entity;
    }

    @Override
    public Page<E> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
}
