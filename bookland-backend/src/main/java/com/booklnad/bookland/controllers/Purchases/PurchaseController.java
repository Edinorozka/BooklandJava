package com.booklnad.bookland.controllers.Purchases;

import com.booklnad.bookland.DB.entity.BooksInPurchases;
import com.booklnad.bookland.DB.entity.Purchase;
import com.booklnad.bookland.DB.entity.User;
import com.booklnad.bookland.DB.repository.BookRepository;
import com.booklnad.bookland.DB.repository.BooksInPurchasesRepository;
import com.booklnad.bookland.DB.repository.PurchaseRepository;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.responses.GetPurchasePesponse;
import com.booklnad.bookland.enums.PurchasesTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = "/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private BooksInPurchasesRepository booksInPurchasesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/size")
    public ResponseEntity<Integer> getPurchaseSize(@RequestParam(value = "user", required = false) int user_id){
        if(user_id != 0){
            Optional<User> user = userRepository.findById(user_id);
            if(user.isEmpty())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(purchaseRepository.findByUser(user.get()).size());
        }
        else
            return ResponseEntity.ok(purchaseRepository.findAll().size());
    }

    @GetMapping("/")
    public ResponseEntity<List<GetPurchasePesponse>> getPurchases(@RequestParam(value = "user", required = false) int user_id,
                                                                  @RequestParam(value = "page") int page){

        Pageable pageable = PageRequest.of(page, 30);
        if(user_id != 0){
            Optional<User> user = userRepository.findById(user_id);
            if(user.isEmpty())
                return ResponseEntity.notFound().build();
            List<GetPurchasePesponse> result = new ArrayList<>();
            for (Purchase p : purchaseRepository.findByUser(user.get(), pageable)){
                if(p.getType() != PurchasesTypes.ShoppingCart) {
                    Set<BooksInPurchases> booksInPurchases = booksInPurchasesRepository.findByPurchase(p);
                    result.add(new GetPurchasePesponse(p, booksInPurchases));
                }
            }
            return ResponseEntity.ok(result);
        }
        else{
            List<GetPurchasePesponse> result = new ArrayList<>();
            for (Purchase p : purchaseRepository.findAll(pageable)){
                Set<BooksInPurchases> booksInPurchases = booksInPurchasesRepository.findByPurchase(p);
                result.add(new GetPurchasePesponse(p, booksInPurchases));
            }
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<GetPurchasePesponse> getPurchase(@PathVariable long id){
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if (purchase.isPresent()) {
            Set<BooksInPurchases> booksInPurchases = booksInPurchasesRepository.findByPurchase(purchase.get());
            return ResponseEntity.ok(new GetPurchasePesponse(purchase.get(), booksInPurchases));
        }
        else
            return ResponseEntity.notFound().build();
    }
}
