package org.reiikaw.moviesrest.dto.response;

import org.springframework.data.domain.Page;

public record ObjectListResponse<T>(
        String response,
        String code,
        Page<T> objects
) { }
