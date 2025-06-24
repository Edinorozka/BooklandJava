package com.booklnad.bookland.dto.responses;

import com.booklnad.bookland.DB.entity.Author;
import com.booklnad.bookland.DB.entity.Book;
import com.booklnad.bookland.DB.entity.BookImages;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BooksCards {
    private Integer isbn;
    private String name;
    private Set<Author> authors;
    private int prise;
    private int quantity;
    private List<BookImages> images;

    public BooksCards(Book book) {
        this.isbn = book.getIsbn();
        this.name = book.getName();
        this.authors = book.getAuthors();
        this.prise = book.getPrise();
        this.images = book.getImages();
        this.quantity = book.getQuantity();
    }
}
