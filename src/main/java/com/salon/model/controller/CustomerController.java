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

    // =========================
    // GET ALL CUSTOMERS
    // =========================
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // =========================
    // GET CUSTOMER BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable Long id) {

        return customerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================
    // ADD NEW CUSTOMER
    // =========================
    @PostMapping
    public ResponseEntity<Customer> addCustomer(
            @RequestBody Customer customer) {

        if (customer.getCustomerName() == null ||
                customer.getCustomerName().trim().isEmpty()) {

            return ResponseEntity.badRequest().build();
        }

        if (customer.getPhone() == null ||
                customer.getPhone().trim().isEmpty()) {

            return ResponseEntity.badRequest().build();
        }

        customer.setCustomerId(null);

        Customer savedCustomer =
                customerRepository.save(customer);

        return ResponseEntity.ok(savedCustomer);
    }

    // =========================
    // UPDATE CUSTOMER
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customerDetails) {

        return customerRepository.findById(id)
                .map(customer -> {

                    customer.setCustomerName(
                            customerDetails.getCustomerName()
                    );

                    customer.setPhone(
                            customerDetails.getPhone()
                    );

                    customer.setEmail(
                            customerDetails.getEmail()
                    );

                    Customer updatedCustomer =
                            customerRepository.save(customer);

                    return ResponseEntity.ok(updatedCustomer);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================
    // DELETE CUSTOMER
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long id) {

        if (!customerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        customerRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}