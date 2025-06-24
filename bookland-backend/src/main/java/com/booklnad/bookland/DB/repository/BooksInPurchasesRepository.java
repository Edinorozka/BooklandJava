package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.BooksInPurchases;
import com.booklnad.bookland.DB.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BooksInPurchasesRepository extends JpaRepository<BooksInPurchases, Long> {
    Set<BooksInPurchases> findByPurchase(Purchase purchase);
}
