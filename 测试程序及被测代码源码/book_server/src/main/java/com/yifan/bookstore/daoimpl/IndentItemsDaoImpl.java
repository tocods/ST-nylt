package com.yifan.bookstore.daoimpl;

import com.yifan.bookstore.dao.IndentItemsDao;
import com.yifan.bookstore.entry.IndentItems;
import com.yifan.bookstore.repository.IndentItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class IndentItemsDaoImpl implements IndentItemsDao {
    @Autowired
    IndentItemsRepository indentItemsRepository;
    @Override
    public List<IndentItems> getByUsername(String username) {
        return indentItemsRepository.getByUsername(username);
    }

    @Override
    public List<IndentItems> getAll() {
        return indentItemsRepository.getAll();
    }
    @Override
    @Transactional(rollbackFor = {Exception.class})
            //,propagation = Propagation.REQUIRES_NEW)
    public void save(IndentItems indentItems){
        indentItemsRepository.save(indentItems);
       // int result=10/0;
    }
}
