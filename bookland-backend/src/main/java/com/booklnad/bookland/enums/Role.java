package com.booklnad.bookland.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }

    Role(String value) {
        this.value = value;
    }
}
