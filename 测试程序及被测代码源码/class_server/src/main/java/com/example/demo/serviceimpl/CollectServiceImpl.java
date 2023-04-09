package com.example.demo.serviceimpl;

import com.example.demo.dao.CollectDao;
import com.example.demo.entity.Class;
import com.example.demo.entity.Collect;
import com.example.demo.entity.Show;
import com.example.demo.service.ClassServer;
import com.example.demo.service.CollectService;
import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectDao collectDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ClassServer classServer;

    @Override
    public List<Show> getCollectsByUserId(int userId){
        //List<Collect> collects = collectDao.getCollectsByUserId(userId);
        List<Integer> collects = redisService.collects(userId);
        return classServer.getClassFromIds(collects);
    }

    @Override
    public int addToCollect(int userId, int classId){return collectDao.addToCollect(userId, classId);}

    @Override
    public void deleteCollect(int collectId){collectDao.deleteCollect(collectId);}

    @Override
    public Collect getByLikedUserIdAndLikedPostId(int likedClassId, int likedPostId)
    {
        return collectDao.getByLikedUserIdAndLikedPostId(likedClassId, likedPostId);
    }

    @Override
    @Transactional
    public void transLikedFromRedis2DB() {
        List<Collect> list = redisService.getLikedDataFromRedis();
        for (Collect like : list) {
            Collect ul = getByLikedUserIdAndLikedPostId(like.getClassId(), like.getUserId());
            if (ul == null){
                if(like.getStatus() == 0)
                    continue;
                Class choose = classServer.getClassForCollect(like.getClassId());
                ul.setCollectClass(choose);
                collectDao.saveCollect(like);
            }else{
                ul.setStatus(like.getStatus());
                collectDao.saveCollect(ul);
            }
        }
    }
}
