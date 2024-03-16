package org.example.cgi.service;

import org.example.cgi.model.Film;
import org.example.cgi.repo.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class FilmService {

    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> findAllFilms() {
        return filmRepository.findAll();
    }

    public Optional<Film> findFilmById(Long id) {
        return filmRepository.findById(id);
    }

    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }

}
