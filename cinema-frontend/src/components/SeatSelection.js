import React, { useState, useEffect } from 'react';
import axios from 'axios';

function SeatSelection({ filmId, numberOfSeats, onClose, onSeatsConfirmed }) {
    const [availableSeats, setAvailableSeats] = useState([]);
    const [selectedSeats, setSelectedSeats] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/seats/all`)
            .then(response => {
                console.log(response.data);
                setAvailableSeats(response.data);
            })
            .catch(error => console.error('There was an error fetching the seats', error));
    }, [filmId]);

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
            <div style={{ display: 'flex', flexDirection: 'column' }}>
                {Array.from({ length: 10 }).map((_, rowIndex) => (
                    <div key={rowIndex} style={{ margin: '10px 0', display: 'flex' }}>
                        {Array.from({ length: 12 }).map((_, seatIndex) => {
                            const seat = availableSeats.find(seat => seat.row === rowIndex + 1 && seat.seatNumber === seatIndex + 1);
                            const seatId = seat?.id;
                            const isAvailable = !seat?.occupied;
                            const isSelected = selectedSeats.includes(seatId);

                            return (
                                <button
                                    key={`seat-${rowIndex}-${seatIndex}`}
                                    onClick={() => isAvailable && seatId && handleSeatClick(seatId)}
                                    style={{
                                        marginRight: '5px',
                                        background: isSelected ? 'green' : isAvailable ? 'lightgrey' : 'red',
                                        color: 'white'
                                    }}
                                    disabled={!isAvailable}
                                >
                                    {`Rida ${rowIndex + 1}, Koht ${seatIndex + 1}`}
                                </button>
                            );
                        })}
                    </div>
                ))}



            </div>
            <button onClick={confirmSeats}>Kinnita valik</button>
            <button onClick={onClose}>Sulge</button>
        </div>
    );
}

export default SeatSelection;
