package org.example.cgi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Table(name="Seats")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="seat_row")
    private int row;

    @Column(name="seat_number")
    private int seatNumber;

    @Column(name="is_occupied")
    private boolean isOccupied;

    public Seat(int row, int seatNumber, boolean isOccupied) {
        this.row = row;
        this.seatNumber = seatNumber;
        this.isOccupied = isOccupied;
    }
}
