package com.booklnad.bookland.service;

import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.JwtRequest;
import com.booklnad.bookland.dto.JwtResponse;
import com.booklnad.bookland.enums.Role;
import com.booklnad.bookland.security.JwpProvider;
import com.booklnad.bookland.security.JwtAuthentication;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwpProvider jwpProvider;
    private final UserRepository userRepository;

    @Value("${icons.path}")
    private String iconPath;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public JwtResponse login(@NonNull JwtRequest authRequest){
        final User user;
        try {
             user = userService.getUserByLogin(authRequest.getLogin()).orElseThrow(() -> new AuthException("Пользователь не найден"));
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            final String accessToken = jwpProvider.generateAccessToken(user);
            final String refreshToken = jwpProvider.generateRefreshToken(user);
            refreshStorage.put(user.getLogin(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            try {
                throw new AuthException("Неверный пароль");
            } catch (AuthException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken){
        if (jwpProvider.validateRefreshToken(refreshToken)){
            final Claims claims = jwpProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if(saveRefreshToken != null && saveRefreshToken.equals(refreshToken)){
                final User user;
                try {
                    user = userService.getUserByLogin(login).orElseThrow(() -> new AuthException("Пользователь не найден"));
                } catch (AuthException e) {
                    throw new RuntimeException(e);
                }
                final String accessToken = jwpProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken){
        if (jwpProvider.validateRefreshToken(refreshToken)){
            final Claims claims = jwpProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if(saveRefreshToken != null && saveRefreshToken.equals(refreshToken)){
                final User user;
                try {
                    user = userService.getUserByLogin(login).orElseThrow(() -> new AuthException("Пользователь не найден"));
                } catch (AuthException e) {
                    throw new RuntimeException(e);
                }
                final String accessToken = jwpProvider.generateAccessToken(user);
                final String newRefreshToken = jwpProvider.generateRefreshToken(user);
                refreshStorage.put(user.getLogin(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        try {
            throw new AuthException("Невалидный JWT токен");
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }

    public String create(String login,
                         String password,
                         String name,
                         Role role,
                         MultipartFile icon){
        User user;
        if (icon != null) {
            File iconDir = new File(iconPath);
            String iconName = UUID.randomUUID().toString();
            String newIconName = iconName + icon.getOriginalFilename().substring(icon.getOriginalFilename().lastIndexOf('.'));
            try {
                icon.transferTo(new File(iconDir + "/" + newIconName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            user = new User(login, password, name, role, newIconName);
        }
        else user = new User(login, password, name, role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Пользователь добавлен";
    }
}
