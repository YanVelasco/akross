package com.akross.akrossapi.domain.customersDomain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String name,
        String email,
        String phone,
        LocalDateTime createdAt
) {
}
