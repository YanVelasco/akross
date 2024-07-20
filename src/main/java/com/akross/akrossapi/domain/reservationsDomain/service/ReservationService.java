package com.akross.akrossapi.domain.reservationsDomain.service;



import com.akross.akrossapi.domain.customersDomain.dto.CustomerResponseDTO;
import com.akross.akrossapi.domain.customersDomain.entity.CustomersEntity;
import com.akross.akrossapi.domain.customersDomain.service.CustomerService;
import com.akross.akrossapi.domain.reservationsDomain.dto.ReservationRequiredDTO;
import com.akross.akrossapi.domain.reservationsDomain.dto.ReservationResponseDTO;
import com.akross.akrossapi.domain.reservationsDomain.entity.ReservationEntity;
import com.akross.akrossapi.domain.reservationsDomain.entity.StatusEnum;
import com.akross.akrossapi.domain.reservationsDomain.repository.ReservationRepository;
import com.akross.akrossapi.domain.roomsDomain.dto.RoomResponseDTO;
import com.akross.akrossapi.domain.roomsDomain.entity.RoomEntity;
import com.akross.akrossapi.domain.roomsDomain.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final RoomService roomService;
    private final CustomerService customerService;
    private final ReservationRepository reservationRepository;


    public ResponseEntity<Object> createReservation(ReservationRequiredDTO reservationDTO) {
        try {
            CustomerResponseDTO customerDTO = (CustomerResponseDTO) customerService.getCustomerById(reservationDTO.customerId()).getBody();
            RoomResponseDTO roomDTO = (RoomResponseDTO) roomService.getRoomById(reservationDTO.roomId()).getBody();

            if (customerDTO == null || roomDTO == null) {
                throw new Exception("Customer or Room not found");
            }

            CustomersEntity customerEntity = customerService.convertToEntity(customerDTO);
            RoomEntity roomEntity = roomService.convertToEntity(roomDTO);

            var newReservation = ReservationEntity.builder()
                    .checkin(reservationDTO.checkin())
                    .checkout(reservationDTO.checkout())
                    .status(StatusEnum.IN_USE)
                    .customer(customerEntity)
                    .room(roomEntity)
                    .build();

            newReservation = reservationRepository.save(newReservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(newReservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating reservation: " + e.getMessage());
        }
    }



    public ResponseEntity<Object> closeReservation(UUID reservationId) {
        var reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            ReservationEntity updatedReservation = reservation.get();
            updatedReservation.setStatus(StatusEnum.FINISHED);
            reservationRepository.save(updatedReservation);
            return ResponseEntity.ok().body("Reservation closed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        }
    }

    public ResponseEntity<List<Object>> findReservationsByDateRange(String startDate, String endDate) {
        try {
            List<ReservationEntity> reservations = reservationRepository.findByCheckinBetween(startDate, endDate);
            List<ReservationResponseDTO> responseDTOs = new ArrayList<>();
            for (ReservationEntity entity : reservations) {
                ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO(entity.getId(), entity.getCheckin(), entity.getCheckout(), entity.getStatus(), entity.getCustomer().getId(), entity.getRoom().getId());
                responseDTOs.add(reservationResponseDTO);
            }
            return ResponseEntity.ok().body(Collections.singletonList(responseDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList("Error finding reservations by date range: " + e.getMessage()));
        }
    }

    public ResponseEntity<List<Object>> findCurrentlyOccupiedRooms() {
        try {
            List<ReservationEntity> occupiedReservations = reservationRepository.findByStatus("IN_USE");
            List<ReservationResponseDTO> responseDTOs = new ArrayList<>();
            for (ReservationEntity entity : occupiedReservations) {
                UUID customerId = entity.getCustomer() != null ? entity.getCustomer().getId() : null;
                UUID roomId = entity.getRoom() != null ? entity.getRoom().getId() : null;
                ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO(
                        entity.getId(),
                        entity.getCheckin(),
                        entity.getCheckout(),
                        entity.getStatus(),
                        customerId,
                        roomId
                );
                responseDTOs.add(reservationResponseDTO);
            }
            return ResponseEntity.ok().body(Collections.singletonList(responseDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList("Error finding occupied rooms: " + e.getMessage()));
        }
    }

}