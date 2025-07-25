package com.booklnad.bookland.controllers.blog;

import com.booklnad.bookland.DB.entity.Articles;
import com.booklnad.bookland.dto.requests.ArticleRequest;
import com.booklnad.bookland.service.ArticlesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/article")
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    @PostMapping(path = "/createArticle")
    public ResponseEntity<String> createArticle(@RequestBody ArticleRequest article){
        try {
            articlesService.saveNewArticle(article);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/delete/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable int articleId){
        try{
            articlesService.deleteArticle(articleId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> updateArticle(@RequestBody Articles article){
        try{
            articlesService.updateArticle(article);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
