import React, { useState, useEffect } from 'react';
import axios from 'axios';

function SeatSelection({ filmId, numberOfSeats, onClose, onSeatsConfirmed }) {
    const [availableSeats, setAvailableSeats] = useState([]);
    const [selectedSeats, setSelectedSeats] = useState([]);
    const [suggestedSeats, setSuggestedSeats] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/seats/all`)
            .then(response => {
                setAvailableSeats(response.data);
            })
            .catch(error => console.error('There was an error fetching the seats', error));

        if (numberOfSeats > 0) {
            axios.get(`http://localhost:8080/api/seats/suggest?requestedSeats=${numberOfSeats}`)
                .then(response => {
                    const suggestedSeatIds = response.data.map(seat => seat.id);
                    setSuggestedSeats(suggestedSeatIds);
                })
                .catch(error => console.error('Error fetching suggested seats', error));
        }
    }, [filmId, numberOfSeats]);

    const handleSeatClick = (seatId) => {
        setSelectedSeats(prevSelectedSeats => {
            const currentIndex = prevSelectedSeats.indexOf(seatId);
            const newSelectedSeats = [...prevSelectedSeats];

            if (currentIndex === -1 && prevSelectedSeats.length < numberOfSeats) {
                newSelectedSeats.push(seatId);
            } else {
                newSelectedSeats.splice(currentIndex, 1);
            }

            return newSelectedSeats;
        });
    };

    const confirmSeats = () => {
        onSeatsConfirmed(selectedSeats);
        onClose();
    };

    return (
        <div>
            <h3>Vali Istekohad</h3>
            <div style={{display: 'flex', flexDirection: 'column'}}>
                {Array.from({length: 10}).map((_, rowIndex) => (
                    <div key={rowIndex} style={{margin: '10px 0', display: 'flex'}}>
                        {Array.from({length: 12}).map((_, seatIndex) => {
                            const seat = availableSeats.find(seat => seat.row === rowIndex + 1 && seat.seatNumber === seatIndex + 1);
                            const seatId = seat?.id;
                            const isAvailable = !seat?.occupied;
                            const isSelected = selectedSeats.includes(seatId);
                            const isSuggested = suggestedSeats.includes(seatId);

                            return (
                                <button
                                    key={`seat-${rowIndex}-${seatIndex}`}
                                    onClick={() => isAvailable && seatId && handleSeatClick(seatId)}
                                    className={`seat ${isSelected ? 'seat-selected' : isSuggested ? 'seat-suggested' : isAvailable ? 'seat-available' : 'seat-unavailable'}`}
                                    disabled={!isAvailable}
                                >
                                    {`Rida ${rowIndex + 1}, Koht ${seatIndex + 1}`}
                                </button>
                            );
                        })}
                    </div>
                ))}
            </div>
            <button onClick={confirmSeats} className="confirm-button">Kinnita valik</button>
            <button onClick={onClose} className="close-button">Sulge</button>

        </div>
    );
}

export default SeatSelection;
