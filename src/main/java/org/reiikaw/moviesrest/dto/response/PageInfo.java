package org.reiikaw.moviesrest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PageInfo {

    private Integer size;
    private Integer page;
    private Long totalCount;
}
