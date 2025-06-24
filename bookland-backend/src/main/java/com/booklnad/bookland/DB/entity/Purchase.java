package com.booklnad.bookland.DB.entity;

import com.booklnad.bookland.enums.PurchasesTypes;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "Purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchasesTypes type;
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<BooksInPurchases> purchaseBooks;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
    @Column
    private String address;
    @Column(columnDefinition = "boolean default false")
    private boolean isPaid;
}
