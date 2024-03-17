package org.example.cgi.service;

import jakarta.annotation.PostConstruct;
import org.example.cgi.model.Seat;
import org.example.cgi.repo.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<Seat> suggestSeats(int requestedSeats) {
        List<Seat> availableSeats = seatRepository.findByIsOccupiedFalse();
        List<Seat> suggestedSeats = suggestFromMiddle(availableSeats, requestedSeats);
        if (suggestedSeats.isEmpty()) {
            suggestedSeats = suggestFromEdges(availableSeats, requestedSeats);
        }
        return suggestedSeats;
    }

    private List<Seat> suggestFromMiddle(List<Seat> availableSeats, int requestedSeats) {
        int middleRowStart = rows / 2 - 1;
        int middleRowEnd = rows / 2 + (rows % 2 == 0 ? 1 : 2);
        return findSequentialSeats(availableSeats, requestedSeats, middleRowStart, middleRowEnd);
    }

    private List<Seat> suggestFromEdges(List<Seat> availableSeats, int requestedSeats) {
        List<Seat> suggestedSeats = findSequentialSeats(availableSeats, requestedSeats, 1, rows / 2 - 2);
        if (suggestedSeats.isEmpty()) {
            suggestedSeats = findSequentialSeats(availableSeats, requestedSeats, rows / 2 + (rows % 2 == 0 ? 2 : 3), rows);
        }
        return suggestedSeats;
    }
    private List<Seat> findSequentialSeats(List<Seat> availableSeats, int requestedSeats, int startRow, int endRow) {
        List<Seat> filteredSeats = availableSeats.stream()
                .filter(seat -> seat.getRow() >= startRow && seat.getRow() <= endRow)
                .collect(Collectors.toList());

        filteredSeats.sort(Comparator.comparingInt(Seat::getRow)
                .thenComparingInt(Seat::getSeatNumber));

        for (int i = 0; i < filteredSeats.size(); i++) {
            List<Seat> temp = new ArrayList<>();
            Seat currentSeat = filteredSeats.get(i);
            temp.add(currentSeat);

            for (int j = 1; j < requestedSeats; j++) {
                if (i + j < filteredSeats.size()) {
                    Seat nextSeat = filteredSeats.get(i + j);
                    if (nextSeat.getRow() == currentSeat.getRow() &&
                            nextSeat.getSeatNumber() == currentSeat.getSeatNumber() + j) {
                        temp.add(nextSeat);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            if (temp.size() == requestedSeats) {
                return temp;
            }
        }

        return new ArrayList<>();
    }





    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }
}
