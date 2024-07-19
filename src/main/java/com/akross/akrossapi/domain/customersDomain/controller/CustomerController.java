package com.akross.akrossapi.domain.customersDomain.controller;



import com.akross.akrossapi.domain.customersDomain.dto.CustomerRequiredDTO;
import com.akross.akrossapi.domain.customersDomain.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerRequiredDTO customerRequiredDTO) {
        return customerService.createCustomer(customerRequiredDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable UUID id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@Valid  @PathVariable UUID id, @RequestBody CustomerRequiredDTO customerRequiredDTO) {
        return customerService.updateCustomer(id, customerRequiredDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable UUID id) {
        return customerService.deleteCustomer(id);
    }
}
