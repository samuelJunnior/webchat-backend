package com.samueljunnior.modules.room.dto;

import com.samueljunnior.modules.user.dto.UserDto;

import java.time.Instant;
import java.util.UUID;

public record RoomDTO(
        UUID id,
        String roomName,
        Instant creationTimestamp,
        UserDto owner
) {
}
