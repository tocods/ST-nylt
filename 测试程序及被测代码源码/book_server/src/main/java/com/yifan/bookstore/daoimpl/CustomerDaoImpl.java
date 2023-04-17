package com.yifan.bookstore.daoimpl;

import com.yifan.bookstore.dao.CustomerDao;
import com.yifan.bookstore.entry.Customer;
import com.yifan.bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CustomerDaoImpl implements CustomerDao {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public List<Customer> getAll() {
        return customerRepository.getAll();
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        return customerRepository.getCustomerByUsername(username);
    }
    @Override
    public void save(Customer customer){
        customerRepository.save(customer);
    }
}
