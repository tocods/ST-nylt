package com.yifan.bookstore.dao;

import com.yifan.bookstore.entry.IndentItems;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndentItemsDao {
    List<IndentItems> getByUsername( String username);
    List<IndentItems> getAll();
    void save(IndentItems indentItems);
}
