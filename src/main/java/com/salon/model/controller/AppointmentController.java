package com.salon.model.controller;

import com.salon.model.model.Appointment;
import com.salon.model.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // GET all appointments
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // GET appointment by ID
    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Integer id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    // ADD / BOOK appointment
    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // DELETE appointment
    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Integer id) {

        if (!appointmentRepository.existsById(id)) {
            return "Appointment not found";
        }

        appointmentRepository.deleteById(id);
        return "Appointment deleted successfully";
    }
}