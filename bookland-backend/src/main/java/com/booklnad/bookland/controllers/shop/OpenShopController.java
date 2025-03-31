package com.booklnad.bookland.controllers.shop;

import com.booklnad.bookland.DB.entity.*;
import com.booklnad.bookland.DB.repository.*;
import com.booklnad.bookland.dto.requests.FindBookParam;
import com.booklnad.bookland.enums.TypeArticles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/shop/open")
public class OpenShopController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookSpecification bookSpecification;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @GetMapping("/size")
    @Transactional
    public Integer getBooksSize(@RequestBody FindBookParam find){
        return bookRepository.findAll(bookSpecification.getBooksByParams(find)).size();
    }

    @GetMapping("/")
    @Transactional
    public ResponseEntity<List<Book>> getBooks(@RequestBody FindBookParam find){
        return ResponseEntity.ok(bookRepository.findAll(bookSpecification.getBooksByParams(find)));
    }

    @GetMapping("/book/{isbn}")
    @Transactional
    public ResponseEntity<Book> getOneBook(@PathVariable int isbn){
        ArrayList<Author> authors = authorRepository.findByBookId(isbn);
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()){
            Book b = book.get();
            b.setAuthors(new HashSet<>(authors));
            return ResponseEntity.ok(b);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/types")
    public ResponseEntity<ArrayList<Type>> getTypes(){
        try{
            return ResponseEntity.ok(typeRepository.findSomeTypes(5));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/genres")
    public ResponseEntity<ArrayList<Genre>> getGenres(@RequestParam(value = "find", required = false) String find){
        try{
            if (find != null){
                return ResponseEntity.ok(genreRepository.findSomeGenres("%" + find + "%", 5));
            } else
                return ResponseEntity.ok(genreRepository.findSomeGenres(5));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/series")
    public ResponseEntity<ArrayList<Series>> getSeries(@RequestParam(value = "find", required = false) String find){
        try{
            if (find != null){
                return ResponseEntity.ok(seriesRepository.findSomeSeries("%" + find + "%", 5));
            } else
                return ResponseEntity.ok(seriesRepository.findSomeSeries(5));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/publishers")
    public ResponseEntity<ArrayList<Publisher>> getPublishers(@RequestParam(value = "find", required = false) String find){
        try{
            if (find != null){
                return ResponseEntity.ok(publisherRepository.findSomePublishers("%" + find + "%", 5));
            } else
                return ResponseEntity.ok(publisherRepository.findSomePublishers(5));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/authors")
    public ResponseEntity<ArrayList<Author>> getAuthors(@RequestParam(value = "find", required = false) String find){
        try{
            if (find != null){
                return ResponseEntity.ok(authorRepository.findSomeAuthors("%" + find + "%", 5));
            } else
                return ResponseEntity.ok(authorRepository.findSomeAuthors(5));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
