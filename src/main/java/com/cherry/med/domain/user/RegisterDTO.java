package com.cherry.med.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(

        @NotBlank
        String login,

        @NotBlank
        String password,

        @NotNull
        UserRole role
) {

    public static User toEntity(RegisterDTO dto) {
        return new User(dto.login, dto.password, dto.role);
    }
}
