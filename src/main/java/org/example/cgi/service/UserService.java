package org.example.cgi.service;

import org.example.cgi.model.Film;
import org.example.cgi.model.User;
import org.example.cgi.repo.FilmRepository;
import org.example.cgi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilmRepository filmRepository;

    public void addFilmToWatchHistory(Long userId, Long filmId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film not found"));

        user.getWatchHistory().add(film);

        userRepository.save(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}