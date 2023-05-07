package com.example.demo.service;


import com.example.demo.entity.Show;
import com.example.demo.entity.Uris;

import java.util.List;

public interface RedisService {


    /**
     * 缓存课程展示信息
     */
    List<Show> getClassShow(int page);

    /**
     * 根据标签展示信息
     */
    List<Show> getClassShowByTag(String tag, int page, int userId);


    Uris getClassById(int classId);
}