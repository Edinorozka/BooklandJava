package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    ArrayList<Banner> findAll();
}
