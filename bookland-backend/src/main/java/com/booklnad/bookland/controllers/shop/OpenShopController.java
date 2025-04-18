package com.booklnad.bookland.controllers.shop;

import com.booklnad.bookland.DB.entity.*;
import com.booklnad.bookland.DB.repository.*;
import com.booklnad.bookland.dto.requests.FindBookParam;
import com.booklnad.bookland.dto.responses.AllFinderParams;
import com.booklnad.bookland.dto.responses.BooksCards;
import com.booklnad.bookland.dto.responses.MinMaxPrises;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/shop/open")
public class OpenShopController {
    @Value("${book.image.path}")
    private String booksImagePath;

    @Value("${shopMaterials.path}")
    private String shopMaterialsPath;

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
    public Integer getBooksSize(@ModelAttribute FindBookParam find){
        return bookRepository.findAll(bookSpecification.getBooksByParams(find)).size();
    }

    @GetMapping("/")
    @Transactional
    public ResponseEntity<ArrayList<BooksCards>> getBooks(@ModelAttribute FindBookParam find){
        Specification<Book> specification = bookSpecification.getBooksByParams(find);
        Pageable pageable = PageRequest.of(find.getPage(), 16);
        ArrayList<BooksCards> result = new ArrayList<>();
        if (find.getInName() == null){
            for (Book b : bookRepository.findAll(specification, pageable)){
                result.add(new BooksCards(b));
            }
        } else {
            log.info(find.getInName());
            for (Book b : bookRepository.findAll(specification, pageable).getContent().stream().limit(3).toList()){
                result.add(new BooksCards(b));
            }
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/material/{image}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/webp"})
    public byte[] getMaterial(@PathVariable String image){
        try {
            InputStream is = new FileInputStream(booksImagePath + image);
            byte[] bytesIcon = is.readAllBytes();
            is.close();
            return bytesIcon;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @GetMapping("/allParams")
    public ResponseEntity<AllFinderParams> getAllParams(){
        try{
            AllFinderParams params = new AllFinderParams();
            params.setTypes(typeRepository.findSomeTypes(5));
            params.setGenres(genreRepository.findSomeGenres(5));
            params.setSeries(seriesRepository.findSomeSeries(5));
            params.setPublishers(publisherRepository.findSomePublishers(5));
            params.setAuthors(authorRepository.findSomeAuthors(5));
            return ResponseEntity.ok(params);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
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

    @GetMapping("/allGenres")
    public ResponseEntity<ArrayList<Genre>> getAllGenres(){
        try{
            return ResponseEntity.ok(genreRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/genre")
    public ResponseEntity<Genre> getGenres(@RequestParam(value = "find") Long find){
        try{
            Optional<Genre> genre = genreRepository.findById(find);
            if (genre.isPresent())
                return ResponseEntity.ok(genre.get());
            else
                return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/shopMaterials/{image}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/webp"})
    public byte[] getBackground(@PathVariable String image){
        try {
            InputStream is = new FileInputStream(shopMaterialsPath + image);
            byte[] bytesIcon = is.readAllBytes();
            is.close();
            return bytesIcon;
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    @GetMapping("/oneSeries")
    public ResponseEntity<Series> getOneSeries(@RequestParam(value = "find") Long find){
        try{
            Optional<Series> series = seriesRepository.findById(find);
            if (series.isPresent())
                return ResponseEntity.ok(series.get());
            else
                return ResponseEntity.notFound().build();
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

    @GetMapping("/publisher")
    public ResponseEntity<Publisher> getPublisher(@RequestParam(value = "find") Long find){
        try{
            Optional<Publisher> publisher = publisherRepository.findById(find);
            if (publisher.isPresent())
                return ResponseEntity.ok(publisher.get());
            else
                return ResponseEntity.notFound().build();
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

    @GetMapping("/author")
    public ResponseEntity<Author> getAuthor(@RequestParam(value = "find") Long find){
        try{
            Optional<Author> author = authorRepository.findById(find);
            if (author.isPresent())
                return ResponseEntity.ok(author.get());
            else
                return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/prises")
    public ResponseEntity<MinMaxPrises> getLimitPrises(@ModelAttribute FindBookParam find){
        try{
            ArrayList<Book> books = (ArrayList<Book>) bookRepository.findAll(bookSpecification.getBooksByParams(find));
            int min = books.stream().min(Comparator.comparingInt(Book::getPrise)).get().getPrise();
            int max = books.stream().max(Comparator.comparingInt(Book::getPrise)).get().getPrise();
            return ResponseEntity.ok(new MinMaxPrises(min, max));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
