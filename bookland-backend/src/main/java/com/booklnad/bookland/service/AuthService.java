package com.booklnad.bookland.service;

import com.booklnad.bookland.DB.entity.Authorized;
import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.DB.repository.AuthorizedRepository;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.requests.JwtRequest;
import com.booklnad.bookland.dto.responses.JwtResponse;
import com.booklnad.bookland.enums.Role;
import com.booklnad.bookland.security.JwpProvider;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthorizedRepository authorizedRepository;
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
            final Optional<Authorized> optionalAuthorized = authorizedRepository.findByUser(user);
            Authorized authorized;
            if (optionalAuthorized.isPresent()){
                authorized = optionalAuthorized.get();
                authorized.setDateAuthorization(new Date());
                authorized.setRefreshToken(passwordEncoder.encode(refreshToken));
            } else {
                authorized = new Authorized(user, refreshToken, new Date());
            }
            authorizedRepository.save(authorized);
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
            final User user;
            try {
                user = userService.getUserByLogin(login).orElseThrow(() -> new AuthException("Пользователь не найден"));
            } catch (AuthException e) {
                throw new RuntimeException(e);
            }
            final Optional<Authorized> authorized = authorizedRepository.findByUser(user);
            if (authorized.isPresent()){
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
            final User user;
            try {
                user = userService.getUserByLogin(login).orElseThrow(() -> new AuthException("Пользователь не найден"));
            } catch (AuthException e) {
                throw new RuntimeException(e);
            }
            final Optional<Authorized> authorized = authorizedRepository.findByUser(user);
            if (authorized.isPresent()){
                final String accessToken = jwpProvider.generateAccessToken(user);
                final String newRefreshToken = jwpProvider.generateRefreshToken(user);
                authorizedRepository.updateAuthorized(passwordEncoder.encode(newRefreshToken), new Date(), user.getId());
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        try {
            Optional<Authorized> auth = authorizedRepository.findByRefreshToken(refreshToken);
            if (auth.isPresent()){
                authorizedRepository.delete(auth.get());
            }
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
            String iconName = UUID.randomUUID().toString();
            String newIconName = iconName + icon.getOriginalFilename().substring(icon.getOriginalFilename().lastIndexOf('.'));
            try {
                File iconDir = new File(getClass().getResource(iconPath).toURI());
                icon.transferTo(new File(iconDir + "/" + newIconName));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
            user = new User(login, password, name, role, newIconName);
        }
        else user = new User(login, password, name, role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Пользователь добавлен";
    }

    public void delete(int userId){
        authorizedRepository.deleteToken(userId);
    }
}
