package com.salon.model.repository;

import com.salon.model.model.Stylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StylistRepository extends JpaRepository<Stylist, Integer> {
}