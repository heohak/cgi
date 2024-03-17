import React, { useState } from 'react';

function FilterForm({ onFilterSubmit }) {
  const [genre, setGenre] = useState('');
  const [ageRestriction, setAgeRestriction] = useState('');
  const [startTime, setStartTime] = useState('');
  const [language, setLanguage] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onFilterSubmit({ genre, ageRestriction, startTime, language });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Žanr:</label>
        <input type="text" value={genre} onChange={(e) => setGenre(e.target.value)} />
      </div>
      <div>
        <label>Vanusepiirang:</label>
        <input type="number" value={ageRestriction} onChange={(e) => setAgeRestriction(e.target.value)} />
      </div>
      <div>
        <label>Algus alates (kellaaeg):</label>
        <input type="time" value={startTime} onChange={(e) => setStartTime(e.target.value)} />
      </div>
      <div>
        <label>Keel:</label>
        <input type="text" value={language} onChange={(e) => setLanguage(e.target.value)} />
      </div>
      <button type="submit">Filtreeri</button>
    </form>
  );
}

export default FilterForm;
