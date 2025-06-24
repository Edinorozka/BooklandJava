package com.booklnad.bookland.dto.requests;

import com.booklnad.bookland.enums.TypeArticles;
import lombok.Data;

@Data
public class ArticleRequest {
    private String title;
    private String description;
    private TypeArticles type;
    private String text;
    private int author_id;
    private int book_id;

    public ArticleRequest(String title, String description, TypeArticles type, String text, int author_id, int book_id) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.text = text;
        this.author_id = author_id;
        this.book_id = book_id;
    }
}
