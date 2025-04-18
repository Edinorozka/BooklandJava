package com.booklnad.bookland.dto.responses;

import com.booklnad.bookland.DB.entity.Author;
import com.booklnad.bookland.DB.entity.Banner;
import com.booklnad.bookland.DB.entity.Book;
import com.booklnad.bookland.DB.entity.BookImages;
import lombok.Data;

import java.util.Set;

@Data
public class BannersResponse {
    private Integer id;
    private String title;
    private String type;
    private String text;
    private String color;
    private String textStyle;
    private String textColor;
    private int textSize;
    private String background;
    private String image;
    private BookFromBanner book;

    public BannersResponse(Banner banner) {
        id = banner.getId();
        title = banner.getTitle();
        type = banner.getType();
        text = banner.getText();
        color = banner.getColor();
        textStyle = banner.getTextStyle();
        textColor = banner.getTextColor();
        textSize = banner.getTextSize();
        background = banner.getBackground();
        image = banner.getImage();
        if (banner.getBook() != null)
            book = new BookFromBanner(banner.getBook());
    }

    @Data
    class BookFromBanner {
        private Integer isbn;
        private String name;
        private int prise;
        private BookImages images;
        private Set<Author> authors;
        private String about;

        public BookFromBanner(Book book) {
            this.isbn = book.getIsbn();
            this.name = book.getName();
            this.prise = book.getPrise();
            this.images = book.getImages().getFirst();
            this.authors = book.getAuthors();
            this.about = book.getAbout();
        }
    }
}


