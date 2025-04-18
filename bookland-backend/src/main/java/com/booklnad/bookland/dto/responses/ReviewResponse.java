package com.booklnad.bookland.dto.responses;

import com.booklnad.bookland.DB.entity.Review;
import com.booklnad.bookland.DB.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Data
public class ReviewResponse {
    private Integer id;
    private String text;
    private byte grade;
    private LocalDate publication;
    private Creator user;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.text = review.getText();
        this.grade = review.getGrade();
        this.publication = review.getPublication().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.user = new Creator(review.getUser());
    }

    @Data
    private class Creator {
        private Integer id;
        private String name;
        private String icon;

        public Creator(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.icon = user.getIcon();
        }
    }
}
