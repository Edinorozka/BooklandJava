package com.booklnad.bookland.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum TypeArticles {
    BOOKS("BOOKS"),
    SHOP("SHOP"),
    OTHER("OTHER");

    private final String value;

    TypeArticles(String value) {
        this.value = value;
    }
}
