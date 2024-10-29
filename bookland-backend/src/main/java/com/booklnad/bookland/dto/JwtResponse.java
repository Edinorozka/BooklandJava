package com.booklnad.bookland.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private final String type = "Bearer";
    private String token;
    private String refresh;
}
