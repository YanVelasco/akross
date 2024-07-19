package com.akross.akrossapi.domain.roomsDomain.dto;

import java.util.UUID;

public record RoomResponseDTO(
        UUID id,
        String roomNumber,
        String type,
        double price
) {
}
