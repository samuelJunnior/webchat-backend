package com.samueljunnior.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserDto(
        Long id,
        @NotEmpty String username,
        @Email @NotEmpty String email
) {
}
