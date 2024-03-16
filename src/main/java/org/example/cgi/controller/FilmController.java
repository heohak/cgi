package org.example.cgi.controller;

import org.example.cgi.model.Film;

import org.example.cgi.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.findAllFilms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        return filmService.findFilmById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmService.saveFilm(film);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Long id, @RequestBody Film filmDetails) {
        return filmService.findFilmById(id).map(film -> {
            film.setTitle(filmDetails.getTitle());
            film.setGenre(filmDetails.getGenre());
            film.setAgeRestriction(filmDetails.getAgeRestriction());
            film.setLanguage(filmDetails.getLanguage());
            film.setStartTime(filmDetails.getStartTime());
            Film updatedFilm = filmService.saveFilm(film);
            return ResponseEntity.ok(updatedFilm);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return ResponseEntity.ok().build();
    }


}
