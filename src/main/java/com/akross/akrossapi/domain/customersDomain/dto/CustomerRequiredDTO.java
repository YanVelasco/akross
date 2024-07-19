package com.akross.akrossapi.domain.customersDomain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerRequiredDTO(
        @NotBlank(message = "Name is required")
        String name,

        @Email(message = "Email must be valid")
        @NotBlank(message = "Email is required")
        String email,

        @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Phone must follow the model (XX) XXXXX-XXXX")
        @NotBlank(message = "Phone is required")
        String phone
) {
}
