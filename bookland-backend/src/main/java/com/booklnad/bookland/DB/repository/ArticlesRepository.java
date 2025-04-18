package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Articles;
import com.booklnad.bookland.enums.TypeArticles;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface ArticlesRepository extends JpaRepository<Articles, Integer> {
    Optional<Articles> findByTitle(String title);
    Optional<Articles> findById(int id);
    ArrayList<Articles> findByType(TypeArticles type);
    ArrayList<Articles> findByType(TypeArticles type, Pageable pageable);

    ArrayList<Articles> findAll();

    @Query(nativeQuery = true,
            value = "SELECT * FROM articles where title like %:find% limit 3")
    ArrayList<Articles> findByName3(@Param("find") String find);
}
