package com.yifan.bookstore.dao;

import com.yifan.bookstore.entry.Indent;
import org.springframework.data.repository.query.Param;

public interface IndentDao {
    Indent getIndentByIndentId(int id);
    void save(Indent indent);
}
