package com.booklnad.bookland.DB.entity;

import com.booklnad.bookland.enums.TypeArticles;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

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
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private Date publication;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeArticles type;
    @OneToMany(mappedBy = "article")
    private Set<Materials> materials = new LinkedHashSet<>();
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Articles(String title, String description, String text, User user, Date publication, TypeArticles type, Book book) {
        this.title = title;
        this.description = description;
        this.text = text;
        this.user = user;
        this.publication = publication;
        this.type = type;
        if (book != null)
            this.book = book;
    }
}
