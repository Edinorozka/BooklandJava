package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Articles;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface ArticlesRepository extends JpaRepository<Articles, Integer> {
    Optional<Articles> findByTitle(String title);
    Optional<Articles> findById(int id);

    ArrayList<Articles> findAll();

    @Query(nativeQuery = true,
            value = "SELECT * FROM public.articles where type = :type ORDER BY publication ASC")
    ArrayList<Articles> findAllAsc(@Param("type") String type);

    @Query(nativeQuery = true,
            value = "SELECT * FROM public.articles where type = :type ORDER BY publication DESC")
    ArrayList<Articles> findAllDesc(@Param("type") String type);
}
