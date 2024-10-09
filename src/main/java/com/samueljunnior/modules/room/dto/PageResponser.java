package com.samueljunnior.modules.room.dto;


import java.util.List;

public record PageResponser(
        int totalPages,
        long totalElements,
        List<RoomDTO> elements
) {
}
