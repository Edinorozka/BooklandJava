package com.booklnad.bookland.controllers.Purchases;

import com.booklnad.bookland.DB.entity.Book;
import com.booklnad.bookland.DB.entity.BooksInPurchases;
import com.booklnad.bookland.DB.entity.Purchase;
import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.DB.repository.BookRepository;
import com.booklnad.bookland.DB.repository.BooksInPurchasesRepository;
import com.booklnad.bookland.DB.repository.PurchaseRepository;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.Embeddable.PurchaseBookId;
import com.booklnad.bookland.dto.requests.UpdatePurchaseRequest;
import com.booklnad.bookland.dto.responses.GetPurchasePesponse;
import com.booklnad.bookland.enums.PurchasesTypes;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping(path = "/buy")
public class ShopingController {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private BooksInPurchasesRepository booksInPurchasesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/cart/{userId}")
    @Transactional
    public ResponseEntity<GetPurchasePesponse> getShoppingCart(@PathVariable int userId){
        try{
            Optional<User> user = userRepository.findById(userId);
            if(user.isEmpty())
                return ResponseEntity.notFound().build();
            Optional<Purchase> optionalPurchase = purchaseRepository.findCartByUserAndType(user.get(), PurchasesTypes.ShoppingCart);
            if(optionalPurchase.isPresent()){
                Purchase purchase = optionalPurchase.get();
                Set<BooksInPurchases> booksInPurchases = booksInPurchasesRepository.findByPurchase(purchase);
                return ResponseEntity.ok(new GetPurchasePesponse(purchase, booksInPurchases));
            } else {
                Purchase purchase = new Purchase();
                purchase.setDate(new Date());
                purchase.setType(PurchasesTypes.ShoppingCart);
                purchase.setUser(user.get());
                purchase = purchaseRepository.save(purchase);
                return ResponseEntity.ok(new GetPurchasePesponse(purchase));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/cart/update")
    @Transactional
    public ResponseEntity<GetPurchasePesponse> updateShoppingCart(@RequestBody UpdatePurchaseRequest request){
        try{
            Optional<Purchase> optionalPurchase = purchaseRepository.findById((long) request.getId());
            Set<BooksInPurchases> booksInPurchases;
            if (optionalPurchase.isEmpty())
                return ResponseEntity.notFound().build();
            Purchase purchase = optionalPurchase.get();
            if(request.getType() != null) {
                Set<BooksInPurchases> books = purchase.getPurchaseBooks();

                for (BooksInPurchases b : books){
                    Book book = b.getBook();
                    book.setQuantity(book.getQuantity() - b.getQuantity());
                    bookRepository.save(book);
                }

                purchase.setDate(new Date());
                purchase.setType(PurchasesTypes.fromValue(request.getType()));
            }

            if (request.getAddress() != null)
                purchase.setAddress(request.getAddress());

            if (request.isPaid())
                purchase.setPaid(true);

            if (request.getPurchaseBooks() != null){
                booksInPurchases = booksInPurchasesRepository.findByPurchase(purchase);
                for (UpdatePurchaseRequest.BookInPurchases pb : request.getPurchaseBooks()){

                    Optional<BooksInPurchases> foundBookInPurchase = booksInPurchases.stream()
                            .filter(b -> b.getBook().getIsbn() == pb.getBook_id()).findFirst();

                    if(foundBookInPurchase.isPresent()){
                        BooksInPurchases book = foundBookInPurchase.get();
                        if (pb.getQuantity() > 0){
                            book.setQuantity(pb.getQuantity());
                            booksInPurchasesRepository.save(book);
                        } else {
                            booksInPurchasesRepository.delete(book);
                        }
                    } else {
                        Optional<Book> book = bookRepository.findByIsbn(pb.getBook_id());
                        if(book.isPresent())
                            booksInPurchasesRepository.save(new BooksInPurchases(new PurchaseBookId(purchase.getId(), book.get().getIsbn()), purchase, book.get(), pb.getQuantity()));
                        else
                            return ResponseEntity.notFound().build();
                    }
                }
            }

            purchase = purchaseRepository.save(purchase);
            booksInPurchases = booksInPurchasesRepository.findByPurchase(purchase);
            GetPurchasePesponse getPurchasePesponse = new GetPurchasePesponse(purchase, booksInPurchases);
            return ResponseEntity.ok(getPurchasePesponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
