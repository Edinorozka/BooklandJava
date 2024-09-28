package com.booklnad.bookland.controllers.authorization;

import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.JwtAuthenticationDto;
import com.booklnad.bookland.dto.RefreshTokenDto;
import com.booklnad.bookland.dto.UserCredentialsDto;
import com.booklnad.bookland.dto.UserDto;
import com.booklnad.bookland.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping(path = "/auth")
public class AuthorizationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/{login}")
    public UserDto getUserByLogin(@PathVariable String login) throws ChangeSetPersister.NotFoundException{
        return userService.getUserByLogin(login);
    }

    @PostMapping(path="/singIn/registration")
    public @ResponseBody String addNewUser (@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PostMapping(path = "/singIn/login")
    public ResponseEntity<JwtAuthenticationDto> loginUser(@RequestBody UserCredentialsDto user){
        try {
            JwtAuthenticationDto jwtAuthenticationDto = userService.singIn(user);
            return ResponseEntity.ok(jwtAuthenticationDto);
        } catch (AuthenticationException e){
            throw new RuntimeException("Аутентификация не удалась" + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) throws Exception{
        return userService.refreshToken(refreshTokenDto);
    }

    @DeleteMapping("/delete")
    public @ResponseBody String deleteUser(@RequestBody User user){
        userService.delete(user);
        return ("Пользователь удалён");
    }
}
