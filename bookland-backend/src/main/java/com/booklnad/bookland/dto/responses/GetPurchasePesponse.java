package com.booklnad.bookland.dto.responses;

import com.booklnad.bookland.DB.entity.*;
import com.booklnad.bookland.enums.PurchasesTypes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Data
public class GetPurchasePesponse {
    private Integer id;
    private String date;
    private String type;
    private String address;
    private Set<BookInPurchases> purchaseBooks;
    private UserResponse user;

    public GetPurchasePesponse(Purchase purchase, Set<BooksInPurchases> purchaseBooks) {
        this.id = purchase.getId();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("ru", "RU"));
        this.date = dateFormat.format(purchase.getDate());
        this.type = purchase.getType().getValue();
        this.user = new UserResponse(purchase.getUser());
        this.address = purchase.getAddress();
        this.purchaseBooks = new HashSet<>();
        if (purchaseBooks != null){
            for(BooksInPurchases bip : purchaseBooks){
                this.purchaseBooks.add(new BookInPurchases(bip));
            }
        }
    }

    public GetPurchasePesponse(Purchase purchase) {
        this.id = purchase.getId();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("ru", "RU"));
        this.date = dateFormat.format(purchase.getDate());
        this.type = purchase.getType().getValue();
        this.user = new UserResponse(purchase.getUser());
    }

    @Data
    public class BookInPurchases {
        private BookInPurchse book;
        private int quantity;

        public BookInPurchases(BooksInPurchases book) {
            this.book = new BookInPurchse(book.getBook());
            this.quantity = book.getQuantity();
        }

        @Data
        public class BookInPurchse{
            private Integer isbn;
            private String name;
            private Set<Author> authors = new HashSet<>();
            private List<BookImages> images;
            private int prise;
            private int quantity;

            public BookInPurchse(Book book) {
            this.isbn = book.getIsbn();
            this.name = book.getName();
            this.authors = book.getAuthors();
            this.images = book.getImages();
            this.prise = book.getPrise();
            this.quantity = book.getQuantity();
            }
        }
    }
}
