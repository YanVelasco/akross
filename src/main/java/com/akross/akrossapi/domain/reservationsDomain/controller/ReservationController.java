package com.akross.akrossapi.domain.reservationsDomain.controller;



import com.akross.akrossapi.domain.reservationsDomain.dto.ReservationRequiredDTO;
import com.akross.akrossapi.domain.reservationsDomain.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Object> createReservation(@Valid @RequestBody ReservationRequiredDTO reservationDTO) {
        return reservationService.createReservation(reservationDTO);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Object> closeReservation(@Valid @PathVariable UUID id) {
        return reservationService.closeReservation(id);
    }

    @GetMapping
    public ResponseEntity<List<Object>> findReservationsByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        return reservationService.findReservationsByDateRange(startDate, endDate);
    }

    @GetMapping("/occupied")
    public ResponseEntity<List<Object>> findCurrentlyOccupiedRooms() {
        return reservationService.findCurrentlyOccupiedRooms();
    }
}
