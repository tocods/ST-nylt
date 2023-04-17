package com.yifan.bookstore.daoimpl;

import com.alibaba.fastjson.JSONArray;
import com.yifan.bookstore.dao.BookDao;
import com.yifan.bookstore.entry.Book;
import com.yifan.bookstore.entry.Bookdescription;
import com.yifan.bookstore.entry.tagNode;
import com.yifan.bookstore.repository.BookRepository;
import com.yifan.bookstore.repository.BookdescriptionRepository;
import com.yifan.bookstore.repository.TagNodeRepository;
import com.yifan.bookstore.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookdescriptionRepository bookdescriptionRepository;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    TagNodeRepository tagNodeRepository;
    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Book getBookByBookId(int book_id) {
        Book book=null;
        System.out.println("Searching Book: " + book_id + " in Redis");
        Object p = redisUtil.get("user" + book_id);
        if (p == null) {
            System.out.println("Book: " + book_id + " is not in Redis");
            System.out.println("Searching Book: " + book_id + " in DB");
            book=bookRepository.getBookByBookId(book_id);
            if(book!=null){
            redisUtil.set("user" + book_id, JSONArray.toJSON(book));
            }
        } else {
            book = JSONArray.parseObject(p.toString(), Book.class);
            System.out.println("Book: " + book_id + " is in Redis");

        }
        return book;
        //return bookRepository.getBookByBookId(book_id);
    }

    @Override
    public List<Book> getBookByTag(String name) {
        List<tagNode> tagNodeList = new ArrayList<>();
        List<tagNode> relatedTagNodes = tagNodeRepository.findByRelatedTagNodeAndName(name);
        if (relatedTagNodes != null) tagNodeList.addAll(relatedTagNodes);
        Set<Book> bookList = new HashSet<>();
        for (tagNode tagNode1 : tagNodeList) {
            String name1 = tagNode1.getName();
            List<Book> bookList1 = bookRepository.getBookByType(name1);
            for(Book book: bookList1){
                bookList.add(book);
            }
            }

        return new ArrayList<>(bookList);
    }

    @Override
    public String findBookdescriptionById(int id) {
        Bookdescription bookdescription=bookdescriptionRepository.findByBookId(id);
        return bookdescription.getDescription();
    }

    @Override
    public void save(Book book){
        redisUtil.set("user"+book.getId(), JSONArray.toJSON(book));
        System.out.println("Book has saved in redis");
        bookRepository.save(book);
        System.out.println("Book has saved in DB");
    }
    @Override
    public void delete(Book book){
        redisUtil.del("user"+book.getId());
        System.out.println("Book has been deleted from redis");
        bookRepository.delete(book);
        System.out.println("Book has been deleted from DB");
    }

}
