package org.reiikaw.moviesrest.repository;

import org.reiikaw.moviesrest.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
