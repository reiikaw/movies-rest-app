package org.reiikaw.moviesrest.repository;

import org.reiikaw.moviesrest.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends BaseRepository<Movie, UUID> {

    boolean existsByTitle(String title);
    Page<Movie> findAllByAvailable(boolean available, Pageable pageable);
    Optional<Movie> findByIdAndAvailable(boolean available, UUID id);
}
