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
      <form onSubmit={handleSubmit} className="card">
          <div className="input-group">
              <label>Žanr:</label>
              <input type="text" className="input" value={genre} onChange={(e) => setGenre(e.target.value)}/>
          </div>
          <div className="input-group">
              <label>Vanusepiirang:</label>
              <input type="number" className="input" value={ageRestriction}
                     onChange={(e) => setAgeRestriction(e.target.value)}/>
          </div>
          <div className="input-group">
              <label>Algus alates (kellaaeg):</label>
              <input type="time" className="input" value={startTime} onChange={(e) => setStartTime(e.target.value)}/>
          </div>
          <div className="input-group">
              <label>Keel:</label>
              <input type="text" className="input" value={language} onChange={(e) => setLanguage(e.target.value)}/>
          </div>
          <button type="submit" className="button">Filtreeri</button>
      </form>
  );
}

export default FilterForm;
