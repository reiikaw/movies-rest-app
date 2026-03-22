package org.reiikaw.moviesrest.mapper;

public interface BaseMapper<E, D> {

    E toEntity(D dto);
    E updateEntityFromDto(E entity, D dto);
}
