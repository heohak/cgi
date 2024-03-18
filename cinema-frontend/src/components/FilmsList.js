import React, { useState, useEffect } from 'react';
import axios from 'axios';
import FilterForm from './FilterForm';
import NumberOfSeatsSelection from './NumberOfSeatsSelection';
import SeatSelection from './SeatSelection';
import FilmRecommendations from './FilmRecommendations';

function FilmsList() {
    const userId = 1;
    const [films, setFilms] = useState([]);
    const [filters, setFilters] = useState({});
    const [selectedFilm, setSelectedFilm] = useState(null);
    const [isSelectingNumberOfSeats, setIsSelectingNumberOfSeats] = useState(false);
    const [numberOfSeats, setNumberOfSeats] = useState(1);
    const [isSelectingSeats, setIsSelectingSeats] = useState(false);
    const [confirmedSeats, setConfirmedSeats] = useState([]);
    const [showRecommendations, setShowRecommendations] = useState(false); // Lisatud

    useEffect(() => {
        fetchFilms();
    }, [filters]);

    const fetchFilms = () => {
        const query = new URLSearchParams(filters).toString();
        axios.get(`http://localhost:8080/api/films?${query}`)
            .then(response => {
                setFilms(response.data);
            })
            .catch(error => console.log('Error fetching films:', error));
    };

    const handleFilterSubmit = (newFilters) => {
        setFilters(newFilters);
    };

    const selectFilm = (filmId) => {
        setSelectedFilm(filmId);
        setIsSelectingNumberOfSeats(true);
    };

    const handleSeatsNumberSelected = (number) => {
        setNumberOfSeats(number);
        setIsSelectingNumberOfSeats(false);
        setIsSelectingSeats(true);
    };

    const handleSeatsConfirmed = (seats) => {
        console.log('Confirmed seats:', seats);
        setConfirmedSeats(seats);
        setIsSelectingSeats(false);
        setSelectedFilm(null);
    };

    const closeSeatSelection = () => {
        setIsSelectingSeats(false);
        setSelectedFilm(null);
    };

    return (
        <div>
            <FilterForm onFilterSubmit={handleFilterSubmit}/>
            <button onClick={() => setShowRecommendations(!showRecommendations)}>
                {showRecommendations ? 'Peida Soovitused' : 'Näita Soovitusi'}
            </button>
            {showRecommendations && <FilmRecommendations userId={userId}/>}
            <h2>Filmide Nimekiri</h2>
            {!isSelectingNumberOfSeats && !isSelectingSeats && (
                <ul>
                    {films.map(film => (
                        <li key={film.id}>
                            {film.title} - Žanr: {film.genre}, Vanusepiirang: {film.ageRestriction},
                            Keel: {film.language}, Algusaeg: {film.startTime}
                            <button onClick={() => selectFilm(film.id)} style={{marginLeft: "10px"}}>Vali Film</button>
                        </li>
                    ))}
                </ul>
            )}
            {isSelectingNumberOfSeats && <NumberOfSeatsSelection onSeatsNumberSelected={handleSeatsNumberSelected}/>}
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
