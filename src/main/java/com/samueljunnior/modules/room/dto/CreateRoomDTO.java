package com.samueljunnior.modules.room.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateRoomDTO(
        @NotEmpty String roomName,
        @NotNull Long userId
) {
}
