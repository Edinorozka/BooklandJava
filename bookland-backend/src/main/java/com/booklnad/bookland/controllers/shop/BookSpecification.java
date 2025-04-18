package com.booklnad.bookland.controllers.shop;

import com.booklnad.bookland.DB.entity.Book;
import com.booklnad.bookland.dto.requests.FindBookParam;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class BookSpecification{

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
                Predicate name = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + params.getInName().toLowerCase() + "%");
                Predicate authorName = criteriaBuilder.like(criteriaBuilder.lower(root.get("authors").get("name")), "%" + params.getInName().toLowerCase() + "%");
                Predicate authorSecondName = criteriaBuilder.like(criteriaBuilder.lower(root.get("authors").get("secondName")), "%" + params.getInName().toLowerCase() + "%");
                Predicate authorLastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("authors").get("lastName")), "%" + params.getInName().toLowerCase() + "%");

                Predicate result = criteriaBuilder.or(name, authorName, authorLastName, authorSecondName);

                predicates.add(result);
            }
            if (params.getLowPrise() != 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("prise"), params.getLowPrise()));
            }

            if (params.getHighPrise() != 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("prise"), params.getHighPrise()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
