package com.booklnad.bookland.DB.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "banners")
@Data
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "type")
    private String type;
    @Column(columnDefinition = "TEXT", name = "text")
    private String text;
    @Column(name = "color")
    private String color;
    @Column(name = "text_style")
    private String textStyle;
    @Column(name = "text_color")
    private String textColor;
    @Column(name = "text_size", nullable = false)
    private int textSize;
    @Column(name = "background")
    private String background;
    @OneToOne
    @JoinColumn(name = "book_isbn")
    private Book book;
    @Column(name = "image")
    private String image;
}
