package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int id);
    Optional<User> findByLogin(String login);
}
