package org.reiikaw.moviesrest.controller.contract;

import jakarta.validation.Valid;
import org.reiikaw.moviesrest.dto.auth.AuthRequest;
import org.reiikaw.moviesrest.dto.ObjectResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/api/v1/auth")
public interface AuthControllerApi {

    @RequestMapping(
            path = "/sign-up",
            method = RequestMethod.POST
    )
    ResponseEntity<ObjectResponse> signUp(@RequestBody @Valid AuthRequest request);

    @RequestMapping(
            path = "/sign-in",
            method = RequestMethod.POST
    )
    ResponseEntity<ObjectResponse> signIn(@RequestBody @Valid AuthRequest request);
}
