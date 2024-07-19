package com.akross.akrossapi.domain.reservationsDomain.repository;



import com.akross.akrossapi.domain.reservationsDomain.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findByCheckinBetween(String startDate, String endDate);
    List<ReservationEntity> findByStatus(String status);
}