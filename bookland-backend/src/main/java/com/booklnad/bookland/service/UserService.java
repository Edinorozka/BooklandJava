package com.booklnad.bookland.service;

import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.JwtAuthenticationDto;
import com.booklnad.bookland.dto.RefreshTokenDto;
import com.booklnad.bookland.dto.UserCredentialsDto;
import com.booklnad.bookland.dto.UserDto;
import com.booklnad.bookland.enums.Role;
import com.booklnad.bookland.mapper.UserMapper;
import com.booklnad.bookland.security.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    public String create(UserDto userDto){
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) user.setRole(Role.USER);
        if (user.getName() == null) user.setName(user.getLogin());
        if (userRepository.findByLogin(user.getLogin()).isPresent()){
            return "Пользователь с таким логином уже создан";
        }
        userRepository.save(user);
        return "Пользователь добавлен";
    }

    private User login(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Optional<User> user = userRepository.findByLogin(userCredentialsDto.getLogin());
        if (user.isPresent()){
            User u = user.get();
            if(passwordEncoder.matches(userCredentialsDto.getPassword(), u.getPassword())){
                return u;
            } else {
                throw new AuthenticationException(" Неверный пароль");
            }
        } else {
            throw new AuthenticationException(" Пользователь не найден");
        }
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    @Transactional
    public UserDto getUserByLogin(String login) throws ChangeSetPersister.NotFoundException {
        return userMapper.toDto(userRepository.findByLogin(login)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    private User getByLogin(String login) throws Exception {
        return userRepository.findByLogin(login).orElseThrow(()->
                new Exception(String.format("Пользователь с логином %s не найден", login)));
    }

    public JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = login(userCredentialsDto);
        return jwtService.generateAuthToken(user.getLogin());
    }

    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception{
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)){
            User user = getByLogin(jwtService.getLoginFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getLogin(), refreshToken);
        }
        throw new AuthenticationException("неверный refresh token");
    }
}
