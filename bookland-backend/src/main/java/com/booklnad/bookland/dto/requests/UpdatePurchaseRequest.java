package com.booklnad.bookland.dto.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UpdatePurchaseRequest {
    private int id;
    private Set<BookInPurchases> purchaseBooks;

    private String type;
    private String address;
    private boolean isPaid;
    private String card;
    private String dataCard;
    private String ccv;
    private String cbp;

    @Data
    @NoArgsConstructor
    public static class BookInPurchases{
        private int book_id;
        private int quantity;

        public BookInPurchases(int book_id, int quantity) {
            this.book_id = book_id;
            this.quantity = quantity;
        }
    }
}
