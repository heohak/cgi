package org.example.cgi.service;

import jakarta.annotation.PostConstruct;
import org.example.cgi.model.Seat;
import org.example.cgi.repo.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    private final int rows = 10;
    private final int seatsPerRow = 12;
    private final Random random = new Random();


    @PostConstruct
    public void initializeSeats() {
        generateSeats();
        markSomeSeatsAsOccupied();
    }

    public void generateSeats() {
        if (seatRepository.count() == 0) {
            for (int row = 1; row <= rows; row++) {
                for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                    seatRepository.save(new Seat(row, seatNum, false));
                }
            }
        }
    }

    private void markSomeSeatsAsOccupied() {
        List<Seat> allSeats = seatRepository.findAll();
        int numberOfSeatsToOccupy = allSeats.size() / 10;

        for (int i = 0; i < numberOfSeatsToOccupy; i++) {
            int indexToOccupy = random.nextInt(allSeats.size());
            Seat seatToOccupy = allSeats.get(indexToOccupy);
            seatToOccupy.setOccupied(true);
            seatRepository.save(seatToOccupy);
        }
    }


    public List<Seat> suggestSeats(int numberOfSeats) {
        return seatRepository.findByIsOccupiedFalse().stream().limit(numberOfSeats).toList();
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }
}
