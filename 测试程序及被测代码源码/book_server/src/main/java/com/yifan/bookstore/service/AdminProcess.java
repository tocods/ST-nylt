package com.yifan.bookstore.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

public interface AdminProcess {
    //@CrossOrigin
    //@RequestMapping("admin/getBook")
    String adminGetBook(HttpSession httpSession,String usn);
//@CrossOrigin
   // @RequestMapping("admin/modify_book")
    String modifyBook(HttpSession httpSession,String usn,
                             int book_id, String name,
                             String author, int isbn,
                             int inventory, float price,
                             String image, String type,String description);
//@CrossOrigin
    //@RequestMapping("admin/delete_book")
    String modifyBook(HttpSession httpSession, int book_id);
//@CrossOrigin
    //@RequestMapping("admin/get_user")
    String getUser(HttpSession httpSession,String usn);
//@CrossOrigin
   // @RequestMapping("admin/modify_user")
    String modifyUser(HttpSession httpSession, String username, int isValid);
//@CrossOrigin
    //@RequestMapping("admin/get_indents")
    String modifyUser(HttpSession httpSession,String usn);

}
