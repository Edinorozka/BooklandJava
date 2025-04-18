package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookIsbn(long isbn);
    Page<Review> findByBookIsbn(long isbn, Pageable pageable);
}
