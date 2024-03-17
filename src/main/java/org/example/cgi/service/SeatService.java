package org.example.cgi.service;

import jakarta.annotation.PostConstruct;
import org.example.cgi.model.Seat;
import org.example.cgi.repo.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    private final int rows = 10;
    private final int seatsPerRow = 12;

    @PostConstruct
    public void generateSeats() {
        if (seatRepository.count() == 0) {
            for (int row = 1; row <= rows; row++) {
                for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                    seatRepository.save(new Seat(row, seatNum, false));
                }
            }
        }
    }

    public List<Seat> suggestSeats(int numberOfSeats) {
        return seatRepository.findByIsOccupiedFalse().stream().limit(numberOfSeats).toList();
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }
}
