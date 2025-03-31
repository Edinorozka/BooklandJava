package com.booklnad.bookland.controllers.blog;

import com.booklnad.bookland.DB.entity.Articles;
import com.booklnad.bookland.DB.entity.Comment;
import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.DB.repository.ArticlesRepository;
import com.booklnad.bookland.DB.repository.CommentRepository;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.requests.CommentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/comment")
@RequiredArgsConstructor
public class CommentsController {
    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ArticlesRepository articlesRepository;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest c){
        try{
            Comment comment = new Comment();
            comment.setText(c.getText());
            comment.setDate(new Date());
            Optional<User> author = userRepository.findById(c.getUserId());
            if (author.isPresent()){
                comment.setUser(author.get());
            } else {
                throw new RuntimeException("Пользователь не существует");
            }

            Optional<Articles> article = articlesRepository.findById(c.getArticleId());
            if (article.isPresent()){
                comment.setArticle(article.get());
            } else {
                throw new RuntimeException("Статья не существует");
            }

            commentRepository.save(comment);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> updateComment(@RequestBody CommentRequest comment){
        try{
            Optional<Comment> optionalComment = commentRepository.findById(comment.getId());
            if (optionalComment.isPresent()){
                Comment c = optionalComment.get();
                c.setText(comment.getText());
                c.setDate(new Date());
                commentRepository.save(c);
            } else {
                throw new RuntimeException();
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(path = "/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId){
        try{
            commentRepository.deleteById((long) commentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
