package com.yifan.bookstore.daoimpl;

import com.yifan.bookstore.dao.OrderFormDao;
import com.yifan.bookstore.entry.OrderForm;
import com.yifan.bookstore.repository.OrderFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class OrderFormDaoImpl implements OrderFormDao {
    @Autowired
    OrderFormRepository orderFormRepository;
    @Override
    public List<OrderForm> getOrderFormsByUsernameAndBook_id(String username, int book_id) {
        return orderFormRepository.getOrderFormsByUsernameAndBook_id(username,book_id);
    }

    @Override
    public List<OrderForm> getOrderFormsByUsername(String username) {
        return orderFormRepository.getOrderFormsByUsername(username);
    }

    @Override
    public OrderForm getOrderFormsByOrderId(int orderId) {
        return orderFormRepository.getOrderFormsByOrderId(orderId);
    }
    @Override
    public  void save(OrderForm orderForm){
        orderFormRepository.save(orderForm);
    }
    @Override
    public void  delete(OrderForm orderForm){
        orderFormRepository.delete(orderForm);
    }
}
