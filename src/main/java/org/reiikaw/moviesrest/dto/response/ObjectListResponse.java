package org.reiikaw.moviesrest.dto.response;

import java.util.List;

public record ObjectListResponse<T>(
        String response,
        String code,
        List<T> objects,
        PageInfo pageInfo
) { }
