package com.yifan.bookstore.dao;

import com.yifan.bookstore.entry.OrderForm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderFormDao {
    List<OrderForm> getOrderFormsByUsernameAndBook_id( String username,  int book_id);
    List<OrderForm> getOrderFormsByUsername(String username);
    OrderForm getOrderFormsByOrderId(int orderId);
    void save(OrderForm orderForm);
    void delete(OrderForm orderForm);
}
