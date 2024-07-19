package com.akross.akrossapi.domain.customersDomain.service;

import com.akross.akrossapi.domain.customersDomain.dto.CustomerRequiredDTO;
import com.akross.akrossapi.domain.customersDomain.dto.CustomerResponseDTO;
import com.akross.akrossapi.domain.customersDomain.entity.CustomersEntity;
import com.akross.akrossapi.domain.customersDomain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    // Create
    public ResponseEntity<Object> createCustomer(CustomerRequiredDTO customerRequiredDTO) {
        if (customerRepository.findByEmail(customerRequiredDTO.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Customer with this email already exists.");
        }

        var createdCustomer = CustomersEntity.builder()
                .name(customerRequiredDTO.name())
                .email(customerRequiredDTO.email())
                .phone(customerRequiredDTO.phone())
                .build();

        createdCustomer = customerRepository.save(createdCustomer);

        var response = new CustomerResponseDTO(
                createdCustomer.getId(),
                createdCustomer.getName(),
                createdCustomer.getEmail(),
                createdCustomer.getPhone(),
                createdCustomer.getCreateAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Object> getCustomerById(UUID id) {
        var customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            var response = new CustomerResponseDTO(
                    customer.get().getId(),
                    customer.get().getName(),
                    customer.get().getEmail(),
                    customer.get().getPhone(),
                    customer.get().getCreateAt()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }

    public ResponseEntity<Object> updateCustomer(UUID id, CustomerRequiredDTO customerRequiredDTO) {
        Optional<CustomersEntity> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            CustomersEntity customer = optionalCustomer.get();
            customer.setName(customerRequiredDTO.name());
            customer.setEmail(customerRequiredDTO.email());
            customer.setPhone(customerRequiredDTO.phone());
            customerRepository.save(customer);
            var response = new CustomerResponseDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getCreateAt()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }

    public ResponseEntity<Object> deleteCustomer(UUID id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }

    public CustomersEntity convertToEntity(CustomerResponseDTO dto) {
        return CustomersEntity.builder()
                .id(dto.id())
                .name(dto.name())
                .email(dto.email())
                .phone(dto.phone())
                .createAt(dto.createdAt())
                .build();
    }
}
