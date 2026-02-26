package com.example.controller;

import com.example.entity.CustomerEntity;
import com.example.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerRepository customerRepository;

  public CustomerController(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @PostMapping
  public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerEntity customer) {
    CustomerEntity saved = customerRepository.save(customer);
    return ResponseEntity.ok(saved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable Long id,
                                                       @RequestBody CustomerEntity customer) {
    return customerRepository.findById(id)
        .map(existing -> {
          existing.setFirstName(customer.getFirstName());
          existing.setLastName(customer.getLastName());
          existing.setEmail(customer.getEmail());
          existing.setRegisteredDate(customer.getRegisteredDate());
          CustomerEntity updated = customerRepository.save(existing);
          return ResponseEntity.ok(updated);
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerEntity> getCustomer(@PathVariable Long id) {
    return customerRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<CustomerEntity>> getAllCustomers() {
    return ResponseEntity.ok(customerRepository.findAll());
  }
}