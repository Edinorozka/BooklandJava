package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface TypeRepository extends JpaRepository<Type, Long> {
    ArrayList<Type> findAll();
    @Query(nativeQuery = true, value = "select * from Types limit :limit")
    ArrayList<Type> findSomeTypes(@Param("limit") int limit);
}
