package org.example.cgi.controller;

import org.example.cgi.model.Seat;
import org.example.cgi.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService cinemaHallService;

    @GetMapping("/suggest")
    public List<Seat> suggestSeats(@RequestParam("requestedSeats") int requestedSeats) {
        return cinemaHallService.suggestSeats(requestedSeats);
    }

    @GetMapping("/all")
    public List<Seat> getAllSeats() {
        return cinemaHallService.getAllSeats();
    }
}
