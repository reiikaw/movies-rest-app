package org.reiikaw.moviesrest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {

    @NotBlank(message = "Параметр <username> не может быть пустым")
    private String username;

    @NotNull(message = "Параметр <isAdmin> не может быть пустым")
    private Boolean isAdmin = false;

    @NotNull(message = "Параметр <registeredAt> не может быть пустым")
    private OffsetDateTime registeredAt;
}
