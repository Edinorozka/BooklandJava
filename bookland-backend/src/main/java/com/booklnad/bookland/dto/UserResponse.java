package com.booklnad.bookland.dto;

import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Getter
@Setter
public class UserResponse {
    private Integer id;
    private String login;
    private String name;
    private Role role;
    private String icon;

    public UserResponse(User user) {
        id = user.getId();
        login = user.getLogin();
        name = user.getName();
        role = user.getRole();
        icon = user.getIcon();
    }
}
