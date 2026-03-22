package org.reiikaw.moviesrest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.reiikaw.moviesrest.dto.MovieProcessDto;
import org.reiikaw.moviesrest.entity.Movie;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper extends BaseMapper<Movie, MovieProcessDto> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", source = "rating", defaultValue = "0.0")
    @Mapping(target = "available", source = "available", defaultValue = "true")
    Movie toEntity(MovieProcessDto dto);

    @Mapping(target = "id", ignore = true)
    Movie updateEntityFromDto(@MappingTarget Movie targetMovie, MovieProcessDto dto);
}
