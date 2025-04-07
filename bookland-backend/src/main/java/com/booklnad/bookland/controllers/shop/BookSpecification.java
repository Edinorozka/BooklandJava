package com.booklnad.bookland.controllers.shop;

import com.booklnad.bookland.DB.entity.Book;
import com.booklnad.bookland.dto.requests.FindBookParam;
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

            if (params.getAuthor() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("authors").get("id"), params.getAuthor()));
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
            if (params.getSeries() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("series").get("id"), params.getSeries()));
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
