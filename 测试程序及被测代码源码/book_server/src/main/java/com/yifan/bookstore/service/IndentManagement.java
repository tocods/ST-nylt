package com.yifan.bookstore.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

public interface IndentManagement {
 //@CrossOrigin
    //@RequestMapping("/purchase/create_indent")
    String createIndent(String usn);
//@CrossOrigin
   // @RequestMapping("/fetch_indents")
    String fetchIndents(HttpSession httpSession,String usn, String book_filter,
                        String author_filter, String time_filter);
    //@CrossOrigin
    //@RequestMapping("statindents")
    String statIdents(HttpSession httpSession,String usn);
}
