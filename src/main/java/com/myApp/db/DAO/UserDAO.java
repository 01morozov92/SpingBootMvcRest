package com.myApp.db.DAO;

import com.myApp.db.model.User;
import com.myApp.db.repo.UserRepository;
import com.myApp.exceptions.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserDAO {

    private final Comparator<User> comparator = Comparator.comparingLong(User::getId);
    private final UserRepository userRepository;

    @Autowired
    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(UserNotFoundException::new);
    }

    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        users.sort(comparator);
        return users;
    }

    @Transactional
    public void removeUser(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public void removeUserById(Long id) {
        userRepository.removeById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
