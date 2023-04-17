package com.yifan.bookstore.daoimpl;

import com.yifan.bookstore.dao.IndentDao;
import com.yifan.bookstore.entry.Indent;
import com.yifan.bookstore.repository.IndentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class IndentDaoImpl implements IndentDao {
    @Autowired
    IndentRepository indentRepository;
    @Override
    public Indent getIndentByIndentId(int id) {
        return indentRepository.getIndentByIndentId(id);
    }
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void save(Indent indent){
        indentRepository.save(indent);
       // int result=10/0;
    }
}
