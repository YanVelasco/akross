package com.akross.akrossapi.domain.roomsDomain.controller;


import com.akross.akrossapi.domain.roomsDomain.dto.RoomRequiredDTO;
import com.akross.akrossapi.domain.roomsDomain.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Object> createRoom(@Valid @RequestBody RoomRequiredDTO roomRequiredDTO) {
        return roomService.createRoom(roomRequiredDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoomById(@PathVariable UUID id) {
        return roomService.getRoomById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRoom(@Valid @PathVariable UUID id, @RequestBody RoomRequiredDTO roomRequiredDTO) {
        return roomService.updateRoom(id, roomRequiredDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable UUID id) {
        return roomService.deleteRoom(id);
    }
}
