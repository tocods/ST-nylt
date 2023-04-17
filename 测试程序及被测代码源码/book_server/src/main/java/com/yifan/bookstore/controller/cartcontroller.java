package com.yifan.bookstore.controller;
import com.yifan.bookstore.service.AdminProcess;
import com.yifan.bookstore.service.CartManagement;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@org.springframework.stereotype.Controller
@CrossOrigin
@RestController
public class cartcontroller {
    @Autowired
    CartManagement cartManagement;
    @RequestMapping("/getBook")
    String getBooklist()throws IOException, SolrServerException {
        return cartManagement.getBooklist();
    }
    @RequestMapping("/searchBook")
    String searchBook(String key)throws IOException,SolrServerException{
       // return cartManagement.searchBook(key);
        return "s";
    }
    @RequestMapping("/getBookByTag")
    String getBookByTag(String key){
        //return cartManagement.getBookByTag(key);
        return "s";
    }
    @RequestMapping("/purchase/add_to_cart")
    String addCart(HttpSession httpSession, String usn,Integer book_id){
        return cartManagement.addCart( usn, book_id);
    }
    @RequestMapping("/purchase/fetch_cart")
    String fetchCart(HttpSession httpSession,String usn){
        return cartManagement.fetchCart( usn);
    }
    @RequestMapping("/purchase/change_amount")
    String changeAmount(HttpSession httpSession, int order_id, int new_amount){
        return cartManagement.changeAmount( order_id, new_amount);
    }
}
