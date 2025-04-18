package com.booklnad.bookland.dto.requests;

import lombok.Data;

@Data
public class CreateReviewRequest {
    private long id;
    private int author_id;
    private String text;
    private int book_id;
    private byte grade;
}
