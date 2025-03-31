package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    ArrayList<Genre> findAll();
    @Query(nativeQuery = true, value = "select * from Genres limit :limit")
    ArrayList<Genre> findSomeGenres(@Param("limit") int limit);
    @Query(nativeQuery = true, value = "select * from Genres where name ilike :name limit :limit")
    ArrayList<Genre> findSomeGenres(@Param("name") String find, @Param("limit") int limit);
}
