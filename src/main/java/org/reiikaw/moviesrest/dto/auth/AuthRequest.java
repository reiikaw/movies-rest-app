package org.reiikaw.moviesrest.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {

    @NotBlank(message = "Имя пользователя не должно быть пустое")
    @Pattern(
            regexp = "^[A-Za-z]{5,20}$",
            message = "Имя пользователя должно иметь длину от 5 до 20 символов и иметь символы латинского алфавита в нижним или верхнем регистре"
    )
    private String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Pattern(
            regexp = "^(?=(?:[^a-z]*[a-z]){3})(?=(?:[^A-Z]*[A-Z]){3})(?=(?:[^0-9]*[0-9]){2})[A-Za-z0-9]{8,40}$",
            message = "Пароль должен иметь длину от 8 до 40 символов и содержать минимум 3 символа латинского алфавита в нижнем регистре, 3 символа латинского алфавита в верхнем регистре и 2 цифры от 0 до 9"
    )
    private String password;
}
