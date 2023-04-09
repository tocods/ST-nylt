package com.example.demo.dao;

import com.example.demo.entity.Collect;

import java.util.List;

public interface CollectDao {
    int addToCollect(int userId, int collectId);
    void deleteCollect(int collectId);
    Collect getByLikedUserIdAndLikedPostId(int likedClassId, int likedPostId);
    void saveCollect(Collect collect);
}
