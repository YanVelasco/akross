package com.akross.akrossapi.domain.reservationsDomain.dto;

import com.akross.akrossapi.domain.reservationsDomain.entity.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationRequiredDTO(
        @NotBlank(message = "Check-in date is required") String checkin,
        @NotBlank(message = "Checkout date is required") String checkout,
        @NotNull(message = "Status is required") StatusEnum status, // Use @NotNull here
        @NotNull(message = "Customer ID is required") UUID customerId,
        @NotNull(message = "Room ID is required") UUID roomId
) {
}