package org.reiikaw.moviesrest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.reiikaw.moviesrest.dto.UserDto;
import org.reiikaw.moviesrest.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends BaseMapper<User, UserDto> {

    UserDto toDto(User user);

    @Override
    @Mapping(target = "id", ignore = true)
    User updateEntityFromDto(@MappingTarget User entity, UserDto dto);
}
