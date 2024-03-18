package org.example.cgi.service;

import org.example.cgi.model.Film;
import org.example.cgi.model.User;
import org.example.cgi.repo.FilmRepository;
import org.example.cgi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service

public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserRepository userRepository;



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

    public List<Film> recommendFilmsBasedOnWatchHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Aggregate genres from the user's watched films
        Map<String, Long> genreFrequency = user.getWatchHistory().stream()
                .map(Film::getGenre)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Find the most frequently watched genre
        String mostFrequentGenre = genreFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        // Recommend films based on the most watched genre
        if (mostFrequentGenre != null) {
            return filmRepository.findByGenre(mostFrequentGenre);
        } else {
            return new ArrayList<>();
        }
    }

}
