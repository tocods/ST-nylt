package com.yifan.bookstore.controller;
import com.yifan.bookstore.service.AdminProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
@CrossOrigin
@RestController
public class admincontroller {
    @Autowired
    AdminProcess adminProcess;
    @RequestMapping("admin/getBook")
    String adminGetBook(HttpSession httpSession,String usn){
        return adminProcess.adminGetBook(httpSession, usn);
    }
    @RequestMapping("admin/modify_book")
    String modifyBook(HttpSession httpSession,String usn,
                      int book_id, String name,
                      String author, int isbn,
                      int inventory, float price,
                      String image, String type,String description){
        return adminProcess.modifyBook(httpSession, usn, book_id, name, author, isbn, inventory, price, image, type, description);
    }
    @RequestMapping("admin/delete_book")
    String modifyBook(HttpSession httpSession, int book_id){
        return adminProcess.modifyBook(httpSession, book_id);
    }
    @RequestMapping("admin/get_user")
    String getUser(HttpSession httpSession,String usn){
        return adminProcess.getUser(httpSession, usn);
    }
    @RequestMapping("admin/modify_user")
    String modifyUser(HttpSession httpSession, String username, int isValid){
        return adminProcess.modifyUser(httpSession, username, isValid);
    }
    @RequestMapping("admin/get_indents")
    String modifyUser(HttpSession httpSession,String usn){
        return adminProcess.modifyUser(httpSession, usn);
    }
}
