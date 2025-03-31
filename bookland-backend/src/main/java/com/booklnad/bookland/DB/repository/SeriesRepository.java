package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    ArrayList<Series> findAll();
    ArrayList<Series> findByPublisherId(int publisherId);
    @Query(nativeQuery = true, value = "select * from Series limit :limit")
    ArrayList<Series> findSomeSeries(@Param("limit") int limit);
    @Query(nativeQuery = true, value = "select * from Series where name ilike :name limit :limit")
    ArrayList<Series> findSomeSeries(@Param("name") String find, @Param("limit") int limit);
}
