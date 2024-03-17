import React, { useState, useEffect } from 'react';
import axios from 'axios';
import FilterForm from './FilterForm';
import SeatSelection from './SeatSelection';

function FilmsList() {
    const [films, setFilms] = useState([]);
    const [filters, setFilters] = useState({});
    const [selectedFilm, setSelectedFilm] = useState(null);

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
    };

    return (
        <div>
            <FilterForm onFilterSubmit={handleFilterSubmit} />
            <h2>Filmide Nimekiri</h2>
            <ul>
                {films.map(film => (
                    <li key={film.id} onClick={() => selectFilm(film.id)}>
                        {film.title} - Žanr: {film.genre}, Vanusepiirang: {film.ageRestriction}, Keel: {film.language}, Algusaeg: {film.startTime}
                        <button onClick={() => selectFilm(film.id)}>Vali Istekohad</button>
                    </li>
                ))}
            </ul>
            {selectedFilm && <SeatSelection filmId={selectedFilm} onClose={() => setSelectedFilm(null)} />}
        </div>
    );
}

export default FilmsList;
