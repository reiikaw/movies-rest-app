package org.reiikaw.moviesrest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<E, ID extends Serializable> {

    E save(E entity);
    E update(E entity);
    Optional<E> findById(ID id);
    E deleteById(ID id);
    List<E> findAll();
    Page<E> findAll(Pageable pageable);
}
