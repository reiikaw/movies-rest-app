package org.reiikaw.moviesrest.service.auth;

import org.reiikaw.moviesrest.dto.auth.AuthRequest;
import org.reiikaw.moviesrest.dto.auth.JwtAuthResponse;

public interface AuthService {

    JwtAuthResponse signUp(AuthRequest request);
    JwtAuthResponse signIn(AuthRequest request);
}
