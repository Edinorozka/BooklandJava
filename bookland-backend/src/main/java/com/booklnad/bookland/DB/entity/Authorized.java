package com.booklnad.bookland.DB.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "authorized")
@Data
public class Authorized {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String refreshToken;
    @Column(nullable = false)
    private Date dateAuthorization;

    public Authorized() {
    }

    public Authorized(User user, String refreshToken, Date dateAuthorization) {
        this.user = user;
        this.refreshToken = refreshToken;
        this.dateAuthorization = dateAuthorization;
    }
}
