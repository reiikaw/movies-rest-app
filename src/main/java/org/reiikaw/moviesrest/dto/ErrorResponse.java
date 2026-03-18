package org.reiikaw.moviesrest.dto;

public record ErrorResponse(
        String response,
        String code,
        String error
) { }
