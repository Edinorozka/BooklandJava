package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    ArrayList<Author> findAll();
    @Query(nativeQuery = true, value = "select id, name, second_name, last_name from authors where id in (select author_id from books_authors where book_isbn = :id)")
    ArrayList<Author> findByBookId(int id);
    @Query(nativeQuery = true, value = "select * from Authors limit :limit")
    ArrayList<Author> findSomeAuthors(@Param("limit") int limit);
    @Query(nativeQuery = true, value = "select * from authors where name ilike :name or second_name ilike :name or last_name ilike :name limit :limit")
    ArrayList<Author> findSomeAuthors(@Param("name") String find, @Param("limit") int limit);
}
