package org.example.cgi.repo;

import org.example.cgi.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByIsOccupiedFalse();
}
