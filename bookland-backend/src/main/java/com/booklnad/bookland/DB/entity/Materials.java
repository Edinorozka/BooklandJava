package com.booklnad.bookland.DB.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Materials")
@Data
@NoArgsConstructor
public class Materials {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column
    private String text;
    @Column(nullable = false)
    private String location;
    @ManyToOne
    @JoinColumn(name="article_id", nullable = false)
    private Articles article;

    public Materials(String text, String location, Articles article) {
        this.text = text;
        this.location = location;
        this.article = article;
    }
}
