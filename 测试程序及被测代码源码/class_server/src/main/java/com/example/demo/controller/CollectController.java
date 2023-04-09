package com.example.demo.controller;

import com.example.demo.entity.Collect;
import com.example.demo.entity.Msg;
import com.example.demo.entity.Show;
import com.example.demo.service.CollectService;
import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollectController {
    @Autowired
    private CollectService collectService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/getCollects")
    public List<Show> getCollectsByUserId(@RequestParam("userId") int userId)
    {
        /*List<Collect> cacheList = redisService.getLikedDataByUserId(userId+"");
        cacheList.addAll(collectService.getCollectsByUserId(userId));*/
        return collectService.getCollectsByUserId(userId);
    }

    @RequestMapping("/doCollect")
    public Msg doCollect(@RequestParam("userId")int userId, @RequestParam("classId") int classId)
    {
        Msg msg = new Msg();
        if(redisService.doCollect(classId,userId).equals("success"))
        {
            msg.setState(1);
        }
        else
        {
            msg.setState(0);
        }
        return msg;
    }

    @RequestMapping("/undoCollect")
    public Msg undoCollect(@RequestParam("userId")int userId, @RequestParam("classId") int classId)
    {
        Msg msg = new Msg();
        if(redisService.undoCollect(classId,userId).equals("success"))
        {
            msg.setState(1);
        }
        else
        {
            msg.setState(0);
        }
        return msg;
    }
}
