package com.yifan.bookstore.repository;

import com.yifan.bookstore.entry.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String>{
    @Query("SELECT b from Book b")
    List<Book> getAll();

    @Query("select b from Book b where b.id=:book_id")
    Book getBookByBookId(@Param("book_id") int book_id);
    @Query("select b from Book b where b.type=:type")
    List<Book> getBookByType(@Param("type") String type);
}
