package com.booklnad.bookland.service;

import com.booklnad.bookland.enums.Role;
import com.booklnad.bookland.security.JwtAuthentication;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRole(getRole(claims));
        jwtInfoToken.setLogin(claims.get("login", String.class));
        jwtInfoToken.setName(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<Role> getRole(Claims claims) {
        final String role = claims.get("role", String.class);
        final List<String> roles = new ArrayList<>();
        roles.add(role);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

}
