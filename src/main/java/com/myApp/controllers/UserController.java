package com.myApp.controllers;

import com.myApp.db.DAO.UserDAO;
import com.myApp.db.model.User;
import com.myApp.exceptions.ControllerExceptions;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
class UserController extends ControllerExceptions {
    @NonNull
    private final UserDAO userDao;

    @GetMapping
    List<User> findAll() {
        return userDao.findAllUsers();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    User findOne(@PathVariable Long userId) {
        return userDao.findById(userId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> insert(@RequestBody @Valid User user, BindingResult bindingResult) {
        createErrorMessageFromValidation(bindingResult);
        userDao.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> delete(@PathVariable Long userId) {
        Optional<User> user = Optional.ofNullable(userDao.findById(userId));
        ResponseEntity<?> response = user.isEmpty() ? notFound().build() : noContent().build();
        userDao.removeUserById(userId);
        return response;
    }
}