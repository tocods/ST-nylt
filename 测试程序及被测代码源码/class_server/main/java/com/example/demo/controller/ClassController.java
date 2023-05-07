package com.example.demo.controller;

import com.example.demo.entity.Show;
import com.example.demo.entity.Uris;
import com.example.demo.service.ClassServer;
import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Class;
import java.util.List;

@RestController
public class ClassController {
    @Autowired
    private ClassServer classServer;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/getAllClasses",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Class> getAllClasses(){
        return classServer.getAllClasses();}

    @RequestMapping("/getClassesByTag")
    List<Class> getClassesByTag(@RequestParam("tag") String tag){return classServer.getClassesByTag(tag);}

    @RequestMapping("/getClassByTitle")
    List<Class> getClassByTitle(@RequestParam("title") String title){return classServer.getClassesByTitle(title);}

    @RequestMapping("/getClassByPage")
    List<Show> getClassByPage(@RequestParam("pid")int pid){return redisService.getClassShow(pid);}


    @RequestMapping(value = "/getClassPageByTag",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Show> getClassPageByTag(@RequestParam("tag")String tag, @RequestParam("page")int page, @RequestParam("userid")int userId)
    {
        System.out.println(tag+": "+page);
        return redisService.getClassShowByTag(tag, page,userId);
    }

    @RequestMapping("/getClassComplex")
    List<Show> getClassComplex(@RequestParam(value = "tag", defaultValue = "fit")String tag, @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "dif", required = false)String dif, @RequestParam(value = "method", required = false)String method,
                               @RequestParam(value = "target", required = false)String target)
    {
        return classServer.getClassByMulTAG(tag,dif,method,target);
    }

    @RequestMapping("/getOneClass")
    Uris getOne(@RequestParam("classId") int classId)
    {
        return redisService.getClassById(classId);
    }
}
