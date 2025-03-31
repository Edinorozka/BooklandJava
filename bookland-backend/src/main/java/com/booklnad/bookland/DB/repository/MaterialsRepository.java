package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Materials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialsRepository extends JpaRepository<Materials, Integer> {
    Optional<Materials> findById(int id);
}
