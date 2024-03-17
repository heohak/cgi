package org.example.cgi.controller;

import org.example.cgi.model.Film;

import org.example.cgi.repo.FilmRepository;
import org.example.cgi.repo.FilmsFilters;
import org.example.cgi.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("api/films")
public class FilmController {

    private final FilmService filmService;

    private final FilmRepository filmRepository;

    @Autowired
    public FilmController(FilmService filmService, FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getFilms(@RequestParam(required = false) String genre,
                               @RequestParam(required = false) Integer ageRestriction,
                               @RequestParam(required = false) String language,
                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime) {

        Specification<Film> spec = Specification.where(null);

        if (genre != null && !genre.isEmpty()) {
            spec = spec.and(FilmsFilters.hasGenre(genre));
        }
        if (ageRestriction != null) {
            spec = spec.and(FilmsFilters.hasAgeRestriction(ageRestriction));
        }
        if (language != null && !language.isEmpty()) {
            spec = spec.and(FilmsFilters.hasLanguage(language));
        }
        if (startTime != null) {
            spec = spec.and(FilmsFilters.hasStartTime(startTime));
        }

        return filmRepository.findAll(spec);
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
