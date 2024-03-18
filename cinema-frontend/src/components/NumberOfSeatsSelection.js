import React, { useState } from 'react';

function NumberOfSeatsSelection({ onSeatsNumberSelected }) {
    const [numberOfSeats, setNumberOfSeats] = useState(1);

    const handleSubmit = (e) => {
        e.preventDefault();
        onSeatsNumberSelected(numberOfSeats);
    };

    return (
        <form onSubmit={handleSubmit} className="card">
            <div>
                <label>Mitu istet soovid broneerida?</label>
                <input
                    type="number"
                    value={numberOfSeats}
                    onChange={(e) => setNumberOfSeats(e.target.value)}
                    min="1"
                />
            </div>
            <button type="submit">Vali istekohad</button>
        </form>
    );
}

export default NumberOfSeatsSelection;
