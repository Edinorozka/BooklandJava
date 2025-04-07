package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    ArrayList<Publisher> findAll();
    @Query(nativeQuery = true, value = "select * from Publishers limit :limit")
    ArrayList<Publisher> findSomePublishers(@Param("limit") int limit);
    @Query(nativeQuery = true, value = "select * from Publishers where name ilike :name limit :limit")
    ArrayList<Publisher> findSomePublishers(@Param("name") String find, @Param("limit") int limit);
}
