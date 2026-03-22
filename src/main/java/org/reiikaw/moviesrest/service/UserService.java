package org.reiikaw.moviesrest.service;

import org.reiikaw.moviesrest.dto.UserDto;
import org.reiikaw.moviesrest.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends BaseService<User, UUID, UserDto> {

    User create(User user);
    User getByUsername(String username);
    User getCurrentUser();
    void deleteByUsername(String username);
    UserDetailsService getUserDetailsService();
}
