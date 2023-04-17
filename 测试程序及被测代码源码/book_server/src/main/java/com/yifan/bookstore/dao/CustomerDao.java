package com.yifan.bookstore.dao;

import com.yifan.bookstore.entry.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAll();

    Customer getCustomerByUsername( String username);
    void save(Customer customer);
}
