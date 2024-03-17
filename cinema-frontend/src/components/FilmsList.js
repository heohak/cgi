import React, { useState, useEffect } from 'react';
import axios from 'axios';
import FilterForm from './FilterForm';
import NumberOfSeatsSelection from './NumberOfSeatsSelection';
import SeatSelection from './SeatSelection';

function FilmsList() {
    const [films, setFilms] = useState([]);
    const [filters, setFilters] = useState({});
    const [selectedFilm, setSelectedFilm] = useState(null);
    const [isSelectingNumberOfSeats, setIsSelectingNumberOfSeats] = useState(false);
    const [numberOfSeats, setNumberOfSeats] = useState(1);
    const [isSelectingSeats, setIsSelectingSeats] = useState(false);
    const [confirmedSeats, setConfirmedSeats] = useState([]);

    useEffect(() => {
        fetchFilms(filters);
    }, [filters]);

    const fetchFilms = (filters) => {
        const query = new URLSearchParams(filters).toString();
        axios.get(`http://localhost:8080/api/films?${query}`)
            .then(response => {
                setFilms(response.data);
            })
            .catch(error => console.error('There was an error fetching the films', error));
    };

    const handleFilterSubmit = (filters) => {
        setFilters(filters);
    };

    const selectFilm = (filmId) => {
        setSelectedFilm(filmId);
        setIsSelectingNumberOfSeats(true);
    };

    const handleSeatsNumberSelected = (seatsNumber) => {
        setNumberOfSeats(seatsNumber);
        setIsSelectingNumberOfSeats(false);
        setIsSelectingSeats(true);
    };

    const handleSeatsConfirmed = (selectedSeats) => {
        console.log('Valitud istekohad:', selectedSeats);
        setConfirmedSeats(selectedSeats);
        setIsSelectingSeats(false);
        setSelectedFilm(null);
    };

    const closeSeatSelection = () => {
        setIsSelectingSeats(false);
        setSelectedFilm(null);
    };

    return (
        <div>
            <FilterForm onFilterSubmit={handleFilterSubmit} />
            <h2>Filmide Nimekiri</h2>
            {!isSelectingNumberOfSeats && !isSelectingSeats && (
                <ul>
                    {films.map(film => (
                        <li key={film.id} onClick={() => selectFilm(film.id)}>
                            {film.title} - Žanr: {film.genre}, Vanusepiirang: {film.ageRestriction}, Keel: {film.language}, Algusaeg: {film.startTime}
                        </li>
                    ))}
                </ul>
            )}
            {isSelectingNumberOfSeats && (
                <NumberOfSeatsSelection onSeatsNumberSelected={handleSeatsNumberSelected} />
            )}
            {isSelectingSeats && selectedFilm && (
                <SeatSelection
                    filmId={selectedFilm}
                    numberOfSeats={numberOfSeats}
                    onClose={closeSeatSelection}
                    onSeatsConfirmed={handleSeatsConfirmed}
                />
            )}
        </div>
    );
}

export default FilmsList;
