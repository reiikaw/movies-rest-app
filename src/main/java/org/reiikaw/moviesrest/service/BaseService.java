package org.reiikaw.moviesrest.service;

import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface BaseService<E, ID extends Serializable, D> {

    E save(E entity);
    E update(ID id, D dto);
    E findById(ID id);
    E deleteById(ID id);
    Page<E> findAll(Integer page, Integer size);
}
