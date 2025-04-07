package com.booklnad.bookland.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private int id;
    private String text;
    private int userId;
    private int articleId;

    public CommentRequest(String text, int userId, int articleId) {
        this.text = text;
        this.userId = userId;
        this.articleId = articleId;
    }
}
