package com.booklnad.bookland.DB.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Books")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"authors"})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer isbn;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String about;
    @ManyToMany
    @JoinTable(
            name = "Books_authors",
            joinColumns = @JoinColumn(name = "book_isbn", referencedColumnName = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private Set<Author> authors = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;
    @Column(nullable = false)
    private int release;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private int prise;
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;
    @Column(nullable = false)
    private int pages;
    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;
    @OneToMany(mappedBy = "bookId", fetch = FetchType.EAGER)
    private List<BookImages> images;
}
