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
public class MoviePatchRequest {

    @Size(min = 5, max = 255, message = "Длина параметра <title> должна быть от 5 до 255 символов")
    private String title;

    @Size(min = 3, max = 255, message = "Длина параметра <director> должна быть от 3 до 255 символов")
    private String director;

    @JsonProperty("release_year")
    @Min(value = 1940, message = "Минимальный год для параметра <release_year> равен 1940")
    private Integer releaseYear;

    @Min(value = 0, message = "Рейтинг не может быть меньше 0.0")
    @Max(value = 10, message = "Рейтинг не может быть больше 10.0")
    private Double rating = 0.0;

    private Boolean available = true;
}
