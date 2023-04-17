package com.yifan.bookstore.controller;
import com.yifan.bookstore.WebSocketServer;
import com.yifan.bookstore.service.IndentManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
@CrossOrigin
@RestController
public class indentmanagementcontroller {
    @Autowired
    IndentManagement identManagement;
    @Autowired
    KafkaTemplate<String,String>kafkaTemplate;
    @Autowired
    WebSocketServer ws;
    @RequestMapping("/purchase/create_indent")
    String createIndent(String usn){
        //ws.sendMessageToUser(usn,identManagement.);
        //kafkaTemplate.send("indent1","key",usn);
        return "success";
       // return identManagement.createIndent(usn);
       // System.out.println(usn);
        //return identManagement.createIndent(usn);
    }

    @RequestMapping("/fetch_indents")
    String fetchIndents(HttpSession httpSession,String usn, String book_filter,
                        String author_filter, String time_filter){
        return identManagement.fetchIndents(httpSession, usn, book_filter, author_filter, time_filter);
    }

    @RequestMapping("statindents")
    String statIdents(HttpSession httpSession,String usn){
        return identManagement.statIdents(httpSession, usn);
    }
}
