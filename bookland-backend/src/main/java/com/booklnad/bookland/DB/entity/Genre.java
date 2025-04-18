package com.booklnad.bookland.DB.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Genres")
@Data
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(name = "color")
    private String color;
    @Column(name = "text_style")
    private String textStyle;
    @Column(name = "text_color")
    private String textColor;
    @Column(name = "background")
    private String backgroundLocation;
}
