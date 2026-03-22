package org.reiikaw.moviesrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MovieProcessDto {

    @NotBlank(message = "Параметр <title> не должен быть пустым")
    @Size(min = 5, max = 255, message = "Для параметра <title> длина должна быть от 5 до 255 символов")
    private String title;

    @NotBlank(message = "Параметр <director> не должен быть пустым")
    @Size(min = 3, max = 255, message = "Для параметра <director> длина должна быть от 3 до 255 символов")
    private String director;

    @JsonProperty("release_year")
    @NotNull(message = "Параметр <release_year> не должен быть пустым")
    private Integer releaseYear;

    @Min(0)
    @Max(10)
    private Double rating = 0.0;

    private Boolean available = true;
}
