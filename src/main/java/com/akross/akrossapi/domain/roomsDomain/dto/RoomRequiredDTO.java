package com.akross.akrossapi.domain.roomsDomain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RoomRequiredDTO(
        @NotBlank(message = "Room number is required") String roomNumber,
        @NotBlank(message = "Type is required") String type,
        @Positive(message = "Price must be greater than 0") double price) {
}