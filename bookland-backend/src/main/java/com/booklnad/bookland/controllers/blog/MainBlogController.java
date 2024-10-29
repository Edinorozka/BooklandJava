package com.booklnad.bookland.controllers.blog;

import com.booklnad.bookland.DB.entity.Articles;
import com.booklnad.bookland.DB.repository.ArticlesRepository;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.AllArticlesResponse;
import com.booklnad.bookland.enums.TypeArticles;
import com.booklnad.bookland.service.ArticlesService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(path = "/blog")
public class MainBlogController {
    private static final Logger logger = LoggerFactory.getLogger(MainBlogController.class);

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticlesService articlesService;

    @GetMapping("/")
    public ResponseEntity<List<AllArticlesResponse>> getAllArticles(@RequestParam("sort") boolean isAsc,
                                                                    @RequestParam(value = "type", required = false) String type){
        Sort sort;
        if (isAsc) sort = Sort.by("publication").ascending();
        else sort = Sort.by("publication").descending();
        ArrayList<AllArticlesResponse> allArticlesResponses = new ArrayList<>();
        for (Articles a : type == null ? articlesRepository.findAll(sort) :
                isAsc ? articlesRepository.findAllAsc(type) : articlesRepository.findAllDesc(type)){
            allArticlesResponses.add(articlesService.responseOneArticle(a));
        }

        return ResponseEntity.ok(allArticlesResponses);
    }
}
