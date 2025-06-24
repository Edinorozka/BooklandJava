package com.booklnad.bookland.DB.entity;

import com.booklnad.bookland.dto.Embeddable.PurchaseBookId;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Books_in_Purchases")
@NoArgsConstructor
@AllArgsConstructor
public class BooksInPurchases {
    @EmbeddedId
    private PurchaseBookId id;

    @ManyToOne
    @MapsId("purchaseId")
    @JoinColumn(name = "purchase_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Purchase purchase;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Book book;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1")
    private int quantity;
}
