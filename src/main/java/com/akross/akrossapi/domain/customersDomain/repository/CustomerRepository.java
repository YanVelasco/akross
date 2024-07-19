package com.akross.akrossapi.domain.customersDomain.repository;




import com.akross.akrossapi.domain.customersDomain.entity.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomersEntity, UUID> {
    Optional<CustomersEntity> findByEmail(String email);
}
