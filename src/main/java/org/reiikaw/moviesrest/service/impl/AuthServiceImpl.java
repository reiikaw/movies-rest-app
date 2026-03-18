package org.reiikaw.moviesrest.service.impl;

import lombok.RequiredArgsConstructor;
import org.reiikaw.moviesrest.dto.auth.AuthRequest;
import org.reiikaw.moviesrest.dto.auth.JwtAuthResponse;
import org.reiikaw.moviesrest.entity.User;
import org.reiikaw.moviesrest.exception.ServerLogicException;
import org.reiikaw.moviesrest.repository.UserRepository;
import org.reiikaw.moviesrest.service.AuthService;
import org.reiikaw.moviesrest.service.JwtService;
import org.reiikaw.moviesrest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public JwtAuthResponse signUp(AuthRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .registeredAt(OffsetDateTime.now())
                .build();
        var savedUser = userService.create(user);
        var jwt = jwtService.generateToken(savedUser);
        return new JwtAuthResponse(jwt);
    }

    @Override
    public JwtAuthResponse signIn(AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()
            ));

            var user = userService.getUserDetailsService().loadUserByUsername(request.getUsername());
            var jwt = jwtService.generateToken(user);
            return new JwtAuthResponse(jwt);
        } else {
            throw new ServerLogicException(
                    HttpStatus.NOT_FOUND,
                    "Пользователя с именем <%s> не существует".formatted(request.getUsername())
            );
        }
    }
}
