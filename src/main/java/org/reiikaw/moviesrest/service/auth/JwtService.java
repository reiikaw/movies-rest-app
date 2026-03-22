package org.reiikaw.moviesrest.service.auth;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    boolean isValidToken(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
    <T> T extractClaims(String token, Function<Claims, T> claimsResolver);
    Date extractExpiration(String token);
    Claims exctractAllClaims(String token);
    Key getSigningKey();
}
