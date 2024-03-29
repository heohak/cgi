import React, { useState, useEffect } from 'react';
import axios from 'axios';

function FilmRecommendations() {
    const [recommendations, setRecommendations] = useState([]);
    const userId= 1;

    useEffect(() => {
        const fetchRecommendations = async () => {
            if (!userId) {
                console.error("userId is required for fetching film recommendations");
                return;
            }
            try {
                const response = await axios.get(`http://localhost:8080/api/films/recommendations?userId=${userId}`)

                setRecommendations(response.data);
            } catch (error) {
                console.error('Error fetching film recommendations', error);
            }
        };

        fetchRecommendations();
    }, []);

    return (
        <div className="recommendations">
            <h2 className="header">Soovitatud Filmid</h2>
            <ul className="content">
                {recommendations.map(film => (
                    <li key={film.id} className="card">{film.title} - {film.genre}</li>
                ))}
            </ul>
        </div>
    );
}

export default FilmRecommendations
