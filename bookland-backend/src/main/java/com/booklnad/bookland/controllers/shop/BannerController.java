package com.booklnad.bookland.controllers.shop;

import com.booklnad.bookland.DB.entity.Banner;
import com.booklnad.bookland.DB.repository.BannerRepository;
import com.booklnad.bookland.dto.responses.BannersResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/banner")
public class BannerController {
    @Autowired
    BannerRepository bannerRepository;

    @GetMapping("/open/")
    public ResponseEntity<ArrayList<BannersResponse>> getBanners(){
        try {
            List<Banner> listBanners = bannerRepository.findAll();
            ArrayList<BannersResponse> banners = new ArrayList<>();
            for (Banner b : listBanners){
                banners.add(new BannersResponse(b));
            }
            return ResponseEntity.ok(banners);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
