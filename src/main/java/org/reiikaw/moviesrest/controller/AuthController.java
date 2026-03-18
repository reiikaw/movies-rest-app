package org.reiikaw.moviesrest.controller;

import lombok.RequiredArgsConstructor;
import org.reiikaw.moviesrest.controller.contract.AuthControllerApi;
import org.reiikaw.moviesrest.dto.auth.AuthRequest;
import org.reiikaw.moviesrest.dto.ObjectResponse;
import org.reiikaw.moviesrest.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<ObjectResponse> signUp(AuthRequest request) {
        ObjectResponse responseBody = new ObjectResponse(
                "Пользователь с именем <%s> успешно зарегистрирован",
                HttpStatus.OK.toString(),
                authService.signUp(request)
        );
        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<ObjectResponse> signIn(AuthRequest request) {
        ObjectResponse responseBody = new ObjectResponse(
                "С возвращением, %s!".formatted(request.getUsername()),
                HttpStatus.OK.toString(),
                authService.signIn(request)
        );
        return ResponseEntity.ok(responseBody);
    }
}
