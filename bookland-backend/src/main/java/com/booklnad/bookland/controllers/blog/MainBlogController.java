package com.booklnad.bookland.controllers.blog;

import com.booklnad.bookland.DB.entity.Articles;
import com.booklnad.bookland.DB.entity.Comment;
import com.booklnad.bookland.DB.repository.ArticlesRepository;
import com.booklnad.bookland.DB.repository.CommentRepository;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.responses.ArticlesResponse;
import com.booklnad.bookland.dto.responses.CommentResponse;
import com.booklnad.bookland.enums.TypeArticles;
import com.booklnad.bookland.service.ArticlesService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/blog")
public class MainBlogController {
    @Value("${materials.path}")
    private String materialsPath;

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/size")
    public Integer getAllArticlesSize(@RequestParam(value = "type", required = false) String type){
        int size = type == null ? articlesRepository.findAll().size() : articlesRepository.findAllAsc(type).size();
        return size;
    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<ArticlesResponse>> getAllArticles(@RequestParam("sort") boolean isAsc,
                                                                      @RequestParam(value = "type", required = false) TypeArticles type,
                                                                      @RequestParam("current") int current){
        Sort sort;
        if (isAsc) sort = Sort.by("publication").ascending();
        else sort = Sort.by("publication").descending();
        ArrayList<ArticlesResponse> allArticlesResponses = new ArrayList<>();
        for (Articles a : type == null ? articlesRepository.findAll(PageRequest.of(current, 20, sort)) :
                articlesRepository.findByType(type, PageRequest.of(current, 20, sort))){
            allArticlesResponses.add(articlesService.responseOneArticle(a));
        }
        return ResponseEntity.ok(allArticlesResponses);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<ArticlesResponse> getOneArticles(@PathVariable int articleId){
        Optional<Articles> article = articlesRepository.findById(articleId);
        if (article.isPresent()){
            return ResponseEntity.ok(new ArticlesResponse(article.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/material/{material}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getMaterial(@PathVariable String material){
        try {
            InputStream is = new FileInputStream(materialsPath + material);
            byte[] bytesIcon = is.readAllBytes();
            is.close();
            return bytesIcon;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/comments/{articleId}")
    public List<CommentResponse> getAllComments(@PathVariable int articleId){
        ArrayList<Comment> comments = commentRepository.findByArticleId(articleId);
        ArrayList<CommentResponse> result = new ArrayList<>();
        for (Comment c : comments){
            result.add(new CommentResponse(c));
        }
        return result;
    }
}
