package com.yifan.bookstore.dao;

import com.yifan.bookstore.entry.Book;
import com.yifan.bookstore.entry.Bookdescription;

import java.util.List;

public interface BookDao {
    List<Book> getAll();
    Book getBookByBookId(int book_id);
    List<Book> getBookByTag(String tagname);
    String findBookdescriptionById(int id);
    void save(Book book);
    void delete(Book book);
}
