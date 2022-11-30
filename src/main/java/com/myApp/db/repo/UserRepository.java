package com.myApp.db.repo;

import com.myApp.db.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    void removeById(Long id);
}
