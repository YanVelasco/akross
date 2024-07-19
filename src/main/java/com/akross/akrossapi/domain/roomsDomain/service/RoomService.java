package com.akross.akrossapi.domain.roomsDomain.service;


import com.akross.akrossapi.domain.roomsDomain.dto.RoomRequiredDTO;
import com.akross.akrossapi.domain.roomsDomain.dto.RoomResponseDTO;
import com.akross.akrossapi.domain.roomsDomain.entity.RoomEntity;
import com.akross.akrossapi.domain.roomsDomain.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public ResponseEntity<Object> createRoom(RoomRequiredDTO roomRequiredDTO) {
        try {
            var createdRoom = RoomEntity.builder()
                    .roomNumber(roomRequiredDTO.roomNumber())
                    .type(roomRequiredDTO.type())
                    .price(roomRequiredDTO.price())
                    .build();

            createdRoom = roomRepository.save(createdRoom);

            var response = new RoomResponseDTO(
                    createdRoom.getId(),
                    createdRoom.getRoomNumber(),
                    createdRoom.getType(),
                    createdRoom.getPrice()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating room");
        }
    }

    public ResponseEntity<Object> getRoomById(UUID id) {
        Optional<RoomEntity> room = roomRepository.findById(id);
        if (room.isPresent()) {
            var response = new RoomResponseDTO(
                    room.get().getId(),
                    room.get().getRoomNumber(),
                    room.get().getType(),
                    room.get().getPrice()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        }
    }

    public ResponseEntity<Object> updateRoom(UUID id, RoomRequiredDTO roomRequiredDTO) {
        Optional<RoomEntity> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            RoomEntity room = optionalRoom.get();

            if (roomRequiredDTO.roomNumber() != null) {
                room.setRoomNumber(roomRequiredDTO.roomNumber());
            }
            if (roomRequiredDTO.type() != null) {
                room.setType(roomRequiredDTO.type());
            }
            if (!Optional.of(roomRequiredDTO.price()).get().equals(0.0)) {
                room.setPrice(roomRequiredDTO.price());
            }

            roomRepository.save(room);
            var response = new RoomResponseDTO(
                    room.getId(),
                    room.getRoomNumber(),
                    room.getType(),
                    room.getPrice()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        }
    }

    public ResponseEntity<Object> deleteRoom(UUID id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        }
    }

    public RoomEntity convertToEntity(RoomResponseDTO dto) {
        return RoomEntity.builder()
                .id(dto.id())
                .roomNumber(dto.roomNumber())
                .type(dto.type())
                .price(dto.price())
                .build();
    }
}
