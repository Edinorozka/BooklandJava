package com.booklnad.bookland.dto.responses;

import com.booklnad.bookland.DB.entity.Comment;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class CommentResponse {
    private Integer id;
    private String text;
    private Date date;
    private final Map<String, Object> author = new HashMap<>();

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.date = comment.getDate();
        author.put("id", comment.getUser().getId());
        author.put("name", comment.getUser().getName());
        author.put("icon", comment.getUser().getIcon());
    }
}
