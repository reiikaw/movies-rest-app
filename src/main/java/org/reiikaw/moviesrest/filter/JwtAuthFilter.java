package org.reiikaw.moviesrest.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.reiikaw.moviesrest.exception.ServerLogicException;
import org.reiikaw.moviesrest.service.JwtService;
import org.reiikaw.moviesrest.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer ";

    private final JwtService jwtService;
    private final UserService userService;

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver exceptionResolver;

    public JwtAuthFilter(final JwtService jwtService, final UserService userService,
                         @Qualifier("handlerExceptionResolver") final HandlerExceptionResolver exceptionResolver) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var authHeader = request.getHeader(AUTHORIZATION_HEADER);

            if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_TOKEN)) {
                filterChain.doFilter(request, response);
                return;
            }

            var jwt = authHeader.substring(BEARER_TOKEN.length());
            var username = jwtService.extractUserName(jwt);

            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.getUserDetailsService().loadUserByUsername(username);

                if (jwtService.isValidToken(jwt, userDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authenticationToken);
                    SecurityContextHolder.setContext(context);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            exceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    new ServerLogicException(
                            HttpStatus.UNAUTHORIZED,
                            "JWT provided is expired",
                            e.getClass().getName()
                    )
            );
        } catch (Exception e) {
            exceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    e
            );
        }
    }
}
