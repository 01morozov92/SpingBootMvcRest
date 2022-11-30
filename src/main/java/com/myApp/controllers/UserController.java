package com.myApp.controllers;

import com.myApp.db.DAO.UserDAO;
import com.myApp.db.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
class UserController {
    @NonNull
    private final UserDAO userDao;

    @GetMapping
    List<User> findAll() {
        return userDao.findAllUsers();
    }

    @GetMapping("/{userId}")
    ResponseEntity<User> findOne(@PathVariable Long userId) {
        return userDao.findById(userId)
                .map(ResponseEntity::ok).orElse(notFound().build());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> insert(@RequestBody User user) {
        userDao.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<?> delete(@PathVariable Long userId) {
        ResponseEntity<?> user = userDao.findById(userId).isEmpty() ? notFound().build() : noContent().build();
        userDao.removeUserById(userId);
        return user;
    }
}