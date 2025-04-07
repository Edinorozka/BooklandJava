package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Comment;
import com.booklnad.bookland.DB.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(int id);
    ArrayList<Comment> findByArticleId(int articleId);
    ArrayList<Comment> findByUser(User user);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Comments WHERE article_id = :articleId", nativeQuery = true)
    void deleteArticleComments(@Param("articleId") long articleId);
}
