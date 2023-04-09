package com.example.demo.service;

import com.example.demo.entity.Collect;
import com.example.demo.entity.Show;
import com.example.demo.entity.Uris;

import java.util.List;

public interface RedisService {

    /*
     * 获取Redis中存储的所有收藏数据
     * @return
     */
    List<Collect> getLikedDataFromRedis();

    /**
     * 缓存课程展示信息
     */
    List<Show> getClassShow(int page);

    /**
     * 根据标签展示信息
     */
    List<Show> getClassShowByTag(String tag, int page, int userId);

    /**
     * 获取ids
     */
    void getIds();

    /**
     * 将所选页课程信息缓存
     */

    String doCollect(int postid, int userid);

    String undoCollect(int postod, int userid);

    boolean isMember(int postid, int userid);
    List<Integer> collects(int userid);
    Uris getClassById(int classId);
}