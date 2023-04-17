package com.yifan.bookstore.service;

import org.springframework.context.annotation.Scope;

public interface TimeService {
    void start(String username);
    String end();
}
