package org.example.cgi.service;

import jakarta.annotation.PostConstruct;
import org.example.cgi.model.Film;
import org.example.cgi.model.User;
import org.example.cgi.repo.FilmRepository;
import org.example.cgi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service

public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initializeData() {
        if (filmRepository.count() == 0) {
            List<Film> films = List.of(
                    new Film(null, "Kevade", "Draama", 0, "Eesti", LocalTime.of(18, 0)),
                    new Film(null, "Suvi", "Draama", 0, "Vene", LocalTime.of(19, 0)),
                    new Film(null, "Sügis", "Draama", 16, "Inglise", LocalTime.of(20, 0)),
                    new Film(null, "Talv", "Draama", 0, "Eesti", LocalTime.of(21, 0)),
                    new Film(null, "Tõde ja õigus", "Draama", 0, "Inglise", LocalTime.of(18, 30)),
                    new Film(null, "November", "Fantaasia", 0, "Eesti", LocalTime.of(19, 30)),
                    new Film(null, "Rehepapp", "Fantaasia", 0, "Eesti", LocalTime.of(20, 30)),
                    new Film(null, "Klass", "Draama", 12, "Eesti", LocalTime.of(21, 30)),
                    new Film(null, "Viimne reliikvia", "Ajalugu", 0, "Eesti", LocalTime.of(18, 15)),
                    new Film(null, "Malev", "Komöödia", 0, "Eesti", LocalTime.of(19, 15))
            );
            filmRepository.saveAll(films);
        }

        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("user1");
            userRepository.save(user);

            List<Film> watchedFilms = filmRepository.findAllById(Arrays.asList(1L, 2L, 6L));
            user.setWatchHistory(watchedFilms);
            userRepository.save(user);


        }
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

        Map<String, Long> genreFrequency = user.getWatchHistory().stream()
                .map(Film::getGenre)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        String mostFrequentGenre = genreFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (mostFrequentGenre != null) {
            return filmRepository.findByGenre(mostFrequentGenre);
        } else {
            return new ArrayList<>();
        }
    }

}
