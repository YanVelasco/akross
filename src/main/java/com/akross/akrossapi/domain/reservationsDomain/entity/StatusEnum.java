package com.akross.akrossapi.domain.reservationsDomain.entity;

public enum StatusEnum {

    SCHEDULED,
    IN_USE,
    ABSENCE,
    FINISHED,
    CANCELED;

    public static StatusEnum fromStringIgnoreCase(String status) {
        for (StatusEnum value : StatusEnum.values()) {
            if (value.name().equalsIgnoreCase(status)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}