package com.booklnad.bookland.controllers.shop;

import com.booklnad.bookland.DB.entity.Book;
import com.booklnad.bookland.DB.entity.Review;
import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.DB.repository.BookRepository;
import com.booklnad.bookland.DB.repository.ReviewRepository;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.requests.CreateReviewRequest;
import com.booklnad.bookland.dto.responses.ReviewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/review")
public class ReviewsController {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/open/size")
    @Transactional
    public Integer getReviewsSize(@RequestParam int isbn){
        return reviewRepository.findByBookIsbn(isbn).size();
    }

    @GetMapping("/open/")
    @Transactional
    public ResponseEntity<ArrayList<ReviewResponse>> getReviews(@RequestParam(defaultValue = "0") int page, @RequestParam int isbn){
        try{
            Pageable pageable = PageRequest.of(page, 30);
            Page<Review> reviewPage = reviewRepository.findByBookIsbn(isbn, pageable);
            ArrayList<ReviewResponse> results = new ArrayList<>();
            for(Review r : reviewPage.getContent()){
                results.add(new ReviewResponse(r));
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestBody CreateReviewRequest data){
        Review review = new Review();
        Optional<User> user = userRepository.findById(data.getAuthor_id());
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        review.setUser(user.get());
        review.setText(data.getText());
        Optional<Book> book = bookRepository.findByIsbn(data.getBook_id());
        if(book.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        review.setBook(book.get());
        review.setGrade(data.getGrade());
        review.setPublication(new Date());
        reviewRepository.save(review);
        return ResponseEntity.ok("Обзор добавлен");
    }

    @PutMapping("/change")
    public ResponseEntity<String> changeReview(@RequestBody CreateReviewRequest data){
        Optional<Review> optionalReview = reviewRepository.findById(data.getId());
        if(optionalReview.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Review review = optionalReview.get();
        review.setText(data.getText());
        review.setGrade(data.getGrade());
        reviewRepository.save(review);
        return ResponseEntity.ok("Обзор изменён");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteReview(@RequestParam long reviewId){
        reviewRepository.deleteById(reviewId);
        return ResponseEntity.ok("Статья удалена");
    }
}
