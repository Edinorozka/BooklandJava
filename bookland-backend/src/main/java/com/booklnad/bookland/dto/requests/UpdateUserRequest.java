package com.booklnad.bookland.dto.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserRequest {
    private int id;
    private String name;
    private MultipartFile icon;
    private String role;
    private String password;
}
