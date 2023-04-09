package com.example.demo.daoimpl;

import com.example.demo.dao.CollectDao;
import com.example.demo.entity.Collect;
import com.example.demo.repository.CollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class CollectDaoImpl implements CollectDao {
    @Autowired
    private CollectRepository collectRepository;


    @Override
    public int addToCollect(int userId, int classId)
    {
        Collect collect = new Collect();
        collect.setCreateDate(new Timestamp(System.currentTimeMillis()));
        collect.setClassId(classId);
        collect.setUserId(userId);
        collectRepository.save(collect);
        return collect.getId();
    }

    @Override
    public void deleteCollect(int collectId){collectRepository.deleteByCollectId(collectId);}

    @Override
    public Collect getByLikedUserIdAndLikedPostId(int likedClassId, int likedPostId)
    {
        return collectRepository.getByLikedUserIdAndLikedPostId(likedClassId, likedPostId);
    }

    @Override
    @Transactional
    public void saveCollect(Collect collect)
    {
        collectRepository.save(collect);
    }
}
