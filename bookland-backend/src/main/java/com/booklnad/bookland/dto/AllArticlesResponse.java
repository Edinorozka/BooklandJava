package com.booklnad.bookland.dto;

import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.enums.TypeArticles;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class AllArticlesResponse {
    private final int id;
    private final String title;
    private final String description;
    private final String text;
    private final Map<String, Object> author = new HashMap<>();
    private final String publication;
    private final TypeArticles type;

    public AllArticlesResponse(Integer id, String title, String description, String text, User user, String date, TypeArticles type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.text = text;
        author.put("author_id", user.getId());
        author.put("author_name", user.getName());
        author.put("author_login", user.getLogin());
        publication = date;
        this.type = type;
    }
}
