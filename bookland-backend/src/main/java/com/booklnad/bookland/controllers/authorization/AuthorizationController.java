package com.booklnad.bookland.controllers.authorization;

import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.*;
import com.booklnad.bookland.enums.Role;
import com.booklnad.bookland.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @GetMapping(path = "/{login}")
    public ResponseEntity<UserResponse> getUserByLogin(@PathVariable String login){
        Optional<User> user = userRepository.findByLogin(login);
        final UserResponse userResponse;
        if (user.isPresent()){
            userResponse = new UserResponse(user.get());
        }
        else {
            throw new RuntimeException();
        }
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(path="/singIn/registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<String> addNewUser (@RequestParam String login,
                                                            @RequestParam String password,
                                                            @RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String role,
                                                            @RequestParam(value = "icon", required = false) MultipartFile icon) {
        if (role == null) role = String.valueOf(Role.USER);
        if (name == null) name = login;
        if (userRepository.findByLogin(login).isPresent()){
            return ResponseEntity.badRequest().body("Пользователь с таким логином уже создан");
        }
        return ResponseEntity.ok(authService.create(login, password, name, Role.valueOf(role), icon));
    }

    @PostMapping(path = "/singIn/login")
    public ResponseEntity<?> loginUser(@RequestBody JwtRequest authRequest){
        final JwtResponse token;
        try{
            token = authService.login(authRequest);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage().substring(e.getMessage().indexOf(": ") + 2));
        }

        return ResponseEntity.ok(token);
    }

    @GetMapping(path = "/singIn/{icon}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getIcon(@PathVariable String icon) throws IOException {
        InputStream is = AuthorizationController.class.getResourceAsStream("/data/icons/" + icon);
        try {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/singIn/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshJwtRequest request){
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/allRefresh")
    public ResponseEntity<JwtResponse> allRefresh(@RequestBody RefreshJwtRequest request){
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/delete")
    public @ResponseBody String deleteUser(@RequestBody User user){
        userRepository.delete(user);
        return ("Пользователь удалён");
    }
}
