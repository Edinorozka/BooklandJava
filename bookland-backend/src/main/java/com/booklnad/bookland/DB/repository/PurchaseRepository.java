package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Purchase;
import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.enums.PurchasesTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUser(User user);
    Page<Purchase> findByUser(User user, Pageable pageable);

    Optional<Purchase> findCartByUserAndType(User user, PurchasesTypes type);
}
