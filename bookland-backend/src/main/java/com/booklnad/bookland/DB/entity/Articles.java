package com.booklnad.bookland.DB.entity;

import com.booklnad.bookland.enums.TypeArticles;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;

@Entity
@Table(name = "articles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String text;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private Date publication;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeArticles type;
}
