package org.reiikaw.moviesrest.dto;

public record ObjectResponse(
        String response,
        String code,
        Object object
) { }
