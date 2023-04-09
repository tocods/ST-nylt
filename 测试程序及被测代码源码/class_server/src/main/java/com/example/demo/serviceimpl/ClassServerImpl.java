package com.example.demo.serviceimpl;

import com.example.demo.dao.ClassDao;
import com.example.demo.entity.*;
import com.example.demo.entity.Class;
import com.example.demo.service.ClassServer;
import io.micrometer.core.instrument.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ClassServerImpl implements ClassServer {
    @Autowired
    ClassDao classDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Class> getAllClasses()
    {
        return classDao.getAllClasses();
    }

    @Override
    public List<Class> getClassesByTag(String tag){return classDao.getClassesByTag(tag);}

    @Override
    public List<Class> getClassesByTitle(String title){return classDao.getClassesByTitle(title);}


    @Override
    public Class getClassById(int classId, int from)
    {
        Class choose =  classDao.getClassById(classId, from);
        if(from == 0)
            return choose;
        BoundZSetOperations zSetOps = redisTemplate.boundZSetOps("RANK_TODAY");
        BigDecimal currentScore = new BigDecimal(1+"."+(Integer.MAX_VALUE-classId));
        Double scoreHistory = zSetOps.score(classId);
        if (scoreHistory == null) {
            zSetOps.add(choose.getTitle()+"_"+classId, currentScore.doubleValue());
        }
        else
            zSetOps.add(choose.getTitle()+"_"+classId,scoreHistory+1);
        return choose;
    }

    @Override
    public Class getClassForCollect(int classId){return classDao.getClassForCollect(classId);}


    @Override
    public List<Show> getClassByMulTAG(String tag, String difficulty, String method, String target)
    {
        return classDao.getShowByMulti(tag, difficulty, method, target);
    }

    @Override
    public List<Show> getClassFromIds(List<Integer> ids)
    {
        return classDao.getClassFromIds(ids);
    }

}

