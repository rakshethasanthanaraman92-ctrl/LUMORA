package com.salon.model.controller;

import com.salon.model.model.Stylist;
import com.salon.model.repository.StylistRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stylists")
public class StylistController {

    private final StylistRepository stylistRepository;

    public StylistController(StylistRepository stylistRepository) {
        this.stylistRepository = stylistRepository;
    }

    @GetMapping
    public List<Stylist> getAllStylists() {
        return stylistRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stylist> getStylist(@PathVariable Integer id) {
        return stylistRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Stylist createStylist(@RequestBody Stylist stylist) {
        return stylistRepository.save(stylist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stylist> updateStylist(
            @PathVariable Integer id,
            @RequestBody Stylist stylist) {

        return stylistRepository.findById(id)
                .map(existing -> {
                    existing.setStylistName(stylist.getStylistName());
                    existing.setSpecialization(stylist.getSpecialization());

                    return ResponseEntity.ok(stylistRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStylist(@PathVariable Integer id) {

        if (!stylistRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        stylistRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}