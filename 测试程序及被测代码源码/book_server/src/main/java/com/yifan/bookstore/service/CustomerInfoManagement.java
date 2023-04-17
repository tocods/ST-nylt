package com.yifan.bookstore.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface CustomerInfoManagement {
//@CrossOrigin
   // @RequestMapping("/login/check_session")
    String checkSession(HttpSession httpSession);
//@CrossOrigin
    //@RequestMapping(value = "/login/check", method = RequestMethod.POST)
    String checkLogin(String usn, String psw, HttpSession httpSession);
//@CrossOrigin
    //@RequestMapping("/profile/getinfo")
    String getInfo(HttpServletRequest request,String usn);
//@CrossOrigin
    //@RequestMapping("/profile/update")
    String updateProfile(HttpSession httpSession, String usn,String phone,
                                String email, String name, String address);
//@CrossOrigin
    //@RequestMapping("/signup/process")
    String signUp(HttpServletRequest request, String username, String password,
                  String phone, String email, String address, String realname);
}
