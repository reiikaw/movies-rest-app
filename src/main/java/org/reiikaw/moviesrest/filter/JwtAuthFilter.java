package org.reiikaw.moviesrest.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.reiikaw.moviesrest.exception.ServerLogicException;
import org.reiikaw.moviesrest.service.auth.JwtService;
import org.reiikaw.moviesrest.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer ";

    private static final List<String> PUBLIC_PATHS = Arrays.asList("/api/v1/auth/", "/swagger-ui/", "/swagger-ui/index.html", "/v3/api-docs");
    private static final List<String> PUBLIC_METHODS = Arrays.asList(HttpMethod.OPTIONS.name());

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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            if (isPublicRequest(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            var authHeader = request.getHeader(AUTHORIZATION_HEADER);
            if (StringUtils.isEmpty(authHeader)) {
                throw new ServerLogicException(
                        HttpStatus.UNAUTHORIZED,
                        "Пользователь не авторизован. Отсутствует токен доступа"
                );
            } else if (!StringUtils.startsWith(authHeader, BEARER_TOKEN)) {
                throw new ServerLogicException(
                        HttpStatus.UNAUTHORIZED,
                        "Пользователь не авторизован. Некорректный токен доступа"
                );
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
                            "Пользователь не авторизован. Токен доступа просрочен",
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

    private boolean isPublicRequest(HttpServletRequest request) {
        String reqUri = request.getRequestURI();
        String method = request.getMethod();

        if (PUBLIC_METHODS.contains(method)) {
            return true;
        }

        return PUBLIC_PATHS.stream().anyMatch(reqUri::startsWith);
    }
}
