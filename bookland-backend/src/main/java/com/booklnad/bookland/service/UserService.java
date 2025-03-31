package com.booklnad.bookland.service;

import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.DB.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByLogin(@NonNull String login) {
        return userRepository.findByLogin(login);
    }
}
