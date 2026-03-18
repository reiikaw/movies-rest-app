package org.reiikaw.moviesrest.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtAuthResponse {

    private String token;
}
