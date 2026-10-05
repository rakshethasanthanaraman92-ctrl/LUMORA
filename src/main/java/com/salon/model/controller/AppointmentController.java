package com.salon.model.controller;

import com.salon.model.model.Appointment;
import com.salon.model.repository.AppointmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable Integer id) {
        return appointmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody Appointment appointment) {

        if (appointment.getCustomerId() == null ||
                appointment.getStylistId() == null ||
                appointment.getServiceId() == null ||
                appointment.getAppointmentDate() == null ||
                appointment.getAppointmentTime() == null) {

            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                appointmentRepository.save(appointment)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Integer id,
            @RequestBody Appointment appointment) {

        return appointmentRepository.findById(id)
                .map(existing -> {

                    existing.setCustomerId(appointment.getCustomerId());
                    existing.setStylistId(appointment.getStylistId());
                    existing.setServiceId(appointment.getServiceId());
                    existing.setAppointmentDate(appointment.getAppointmentDate());
                    existing.setAppointmentTime(appointment.getAppointmentTime());

                    return ResponseEntity.ok(
                            appointmentRepository.save(existing)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Integer id) {

        if (!appointmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        appointmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}