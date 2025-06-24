package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Articles;
import com.booklnad.bookland.enums.TypeArticles;
import org.springframework.data.domain.Page;
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
    Page<Articles> findByType(TypeArticles type, Pageable pageable);
    @Query("SELECT a FROM Articles a WHERE a.title LIKE %:title%")
    Page<Articles> findByTitle(@Param("title") String title, Pageable pageable);
    @Query("SELECT a FROM Articles a WHERE a.type = :type AND a.title LIKE %:title%")
    Page<Articles> findByTypeTitle(@Param("type") TypeArticles type, @Param("title") String title, Pageable pageable);

    ArrayList<Articles> findAll();

    @Query(nativeQuery = true,
            value = "SELECT * FROM articles where title like %:find% limit 3")
    ArrayList<Articles> findByName3(@Param("find") String find);
}
