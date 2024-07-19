package com.akross.akrossapi.domain.roomsDomain.repository;




import com.akross.akrossapi.domain.roomsDomain.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
}
