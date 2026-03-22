package org.reiikaw.moviesrest.dto.response;

public record ObjectResponse(
        String response,
        String code,
        Object object
) { }
