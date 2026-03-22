package org.reiikaw.moviesrest.service.impl;

import org.reiikaw.moviesrest.dto.UserDto;
import org.reiikaw.moviesrest.entity.User;
import org.reiikaw.moviesrest.exception.ServerLogicException;
import org.reiikaw.moviesrest.mapper.UserMapper;
import org.reiikaw.moviesrest.repository.UserRepository;
import org.reiikaw.moviesrest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserDto, UUID, UserRepository, UserMapper> implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(final UserRepository repository, final UserMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new ServerLogicException(
                    HttpStatus.BAD_REQUEST,
                    "Пользователь с именем <%s> уже существует".formatted(user.getUsername()));
        }

        return save(user);
    }

    @Override
    public User getByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() ->
                new ServerLogicException(
                        HttpStatus.NOT_FOUND,
                        "Пользователь с именем <%s> не найден".formatted(username))
        );
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        User user = getByUsername(username);
        repository.delete(user);
    }

    @Override
    public UserDetailsService getUserDetailsService() {
        return this::getByUsername;
    }
}
