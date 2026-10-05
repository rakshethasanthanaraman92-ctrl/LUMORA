package com.salon.model.controller;

import com.salon.model.model.Customer;
import com.salon.model.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Get existing customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Add NEW customer
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {

        if (customer.getCustomerName() == null ||
                customer.getCustomerName().trim().isEmpty()) {

            return ResponseEntity.badRequest().build();
        }

        if (customer.getPhone() == null ||
                customer.getPhone().trim().isEmpty()) {

            return ResponseEntity.badRequest().build();
        }

        customer.setCustomerId(null);

        Customer savedCustomer = customerRepository.save(customer);

        return ResponseEntity.ok(savedCustomer);
    }

    // Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {

        return customerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}