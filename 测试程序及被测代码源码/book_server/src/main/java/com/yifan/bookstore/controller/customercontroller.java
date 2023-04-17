package com.yifan.bookstore.controller;
import com.yifan.bookstore.service.CustomerInfoManagement;
import com.yifan.bookstore.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
@CrossOrigin
@RestController
@Scope(value = "session")
public class customercontroller {
    @Autowired
    CustomerInfoManagement customerInfoManagement;
    @Autowired
    TimeService timeService;
    @RequestMapping("/login/check_session")
    String checkSession(HttpSession httpSession){
        return customerInfoManagement.checkSession(httpSession);
    }
    @RequestMapping(value = "/login/check")
    String checkLogin(String usn, String psw, HttpSession httpSession){
        System.out.println(timeService);
        //System.out.println(customercontroller);
        System.out.println(this);
       // timeService.start(usn);
        return customerInfoManagement.checkLogin(usn, psw, httpSession);
    }
    @RequestMapping(value = "/logout")
    @Scope(value = "session")
    String logOut(){
        System.out.println(timeService);
        System.out.println(this);
        return timeService.end();
    }
    @RequestMapping("/profile/getinfo")
    String getInfo(HttpServletRequest request,String usn){
        return customerInfoManagement.getInfo(request, usn);
    }
    @RequestMapping("/profile/update")
    String updateProfile(HttpSession httpSession, String usn,String phone,
                         String email, String name, String address){
        return customerInfoManagement.updateProfile(httpSession, usn, phone, email, name, address);
    }
    @RequestMapping("/signup/process")
    String signUp(HttpServletRequest request, String username, String password,
                  String phone, String email, String address, String realname){
        return customerInfoManagement.signUp(request, username, password, phone, email, address, realname);
    }
}
