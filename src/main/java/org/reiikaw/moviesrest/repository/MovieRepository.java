package org.reiikaw.moviesrest.repository;

import org.reiikaw.moviesrest.entity.Movie;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieRepository extends BaseRepository<Movie, UUID> {

    boolean existsByTitle(String title);
}
