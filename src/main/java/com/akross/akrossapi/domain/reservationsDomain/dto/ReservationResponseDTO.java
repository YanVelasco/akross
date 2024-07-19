package com.akross.akrossapi.domain.reservationsDomain.dto;

import com.akross.akrossapi.domain.reservationsDomain.entity.StatusEnum;
import lombok.Builder;

import java.util.UUID;
@Builder
public record ReservationResponseDTO(
        UUID id,
        String checkin,
        String checkout,
        String status,
        UUID customerId,
        UUID roomId
) {
    public ReservationResponseDTO(UUID id, String checkin, String checkout, StatusEnum status) {
        this(id, checkin, checkout, status.name(), null, null);
    }
}