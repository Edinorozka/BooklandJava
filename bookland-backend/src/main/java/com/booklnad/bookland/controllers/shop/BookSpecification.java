package com.booklnad.bookland.controllers.shop;

import com.booklnad.bookland.DB.entity.Author;
import com.booklnad.bookland.DB.entity.Book;
import com.booklnad.bookland.dto.requests.FindBookParam;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookSpecification {

    public Specification<Book> getBooksByParams(FindBookParam params) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (params.getAuthor() != null && !params.getAuthor().isEmpty()) {
                Join<Book, Author> authorsJoin = root.join("authors");

                if (authorsJoin.get("name") != null) {
                    Predicate nameMatch = criteriaBuilder.like(criteriaBuilder.lower(authorsJoin.get("name")), "%" + params.getAuthor().toLowerCase() + "%");
                    predicates.add(nameMatch);
                }

                if (authorsJoin.get("secondName") != null) {
                    Predicate secondNameMatch = criteriaBuilder.like( criteriaBuilder.lower(authorsJoin.get("secondName")), "%" + params.getAuthor().toLowerCase() + "%");
                    predicates.add(secondNameMatch);
                }

                if (authorsJoin.get("lastName") != null) {
                    Predicate lastNameMatch = criteriaBuilder.like(criteriaBuilder.lower(authorsJoin.get("lastName")), "%" + params.getAuthor().toLowerCase() + "%");
                    predicates.add(lastNameMatch);
                }

                if (!predicates.isEmpty()) {
                    Predicate authorMatch = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
                    predicates.clear();
                    predicates.add(authorMatch);
                }
            }
            if (params.getType() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("type").get("id"), params.getType()));
            }
            if (params.getGenre() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("genre").get("id"), params.getGenre()));
            }
            if (params.getPublisher() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("series").get("publisher").get("id"), params.getPublisher()));
            }
            if (params.getSeires() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("series").get("id"), params.getSeires()));
            }
            if (params.getInName() != null && !params.getInName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + params.getInName().toLowerCase() + "%"));
            }
            if (params.getPrise() != 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("prise"), params.getPrise()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
