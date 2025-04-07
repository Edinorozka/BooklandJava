package com.booklnad.bookland.DB.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BookImages")
@Data
@NoArgsConstructor
public class BookImages {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private String location;
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    public Book bookId;
}
