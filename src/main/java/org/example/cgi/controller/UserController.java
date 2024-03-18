package org.example.cgi.controller;

import org.example.cgi.model.Film;
import org.example.cgi.model.User;
import org.example.cgi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{userId}/watchhistory/{filmId}")
    public ResponseEntity<?> addFilmToWatchHistory(@PathVariable Long userId, @PathVariable Long filmId) {
        userService.addFilmToWatchHistory(userId, filmId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}