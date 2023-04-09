package com.example.demo.service;

import com.example.demo.entity.Collect;
import com.example.demo.entity.Show;

import java.util.List;

public interface CollectService {
    List<Show> getCollectsByUserId(int userId);
    int addToCollect(int userId, int classId);
    void deleteCollect(int collectId);
    void transLikedFromRedis2DB();
    Collect getByLikedUserIdAndLikedPostId(int likedClassId, int likedPostId);
}
