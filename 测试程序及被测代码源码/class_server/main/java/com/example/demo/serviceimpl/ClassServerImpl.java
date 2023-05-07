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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ClassServerImpl implements ClassServer {

    private final String[] tags = {"cycle", "run", "fit", "basketball","soccer"};

    private final String[] diffs = {"low", "mid", "high"};

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
    public List<Class> getClassesByTag(String tag)
    {
        if(!ifExistInTags(tag))
            return Collections.emptyList();
        return classDao.getClassesByTag(tag);
    }

    @Override
    public List<Class> getClassesByTitle(String title){return classDao.getClassesByTitle(title);}


    @Override
    public Class getClassById(int classId, int from)
    {
        Class choose =  classDao.getClassById(classId, from);
        if(from <= 0 || classId < 0)
            return choose;
        BigDecimal currentScore = new BigDecimal(1+"."+(Integer.MAX_VALUE-classId));
        Double scoreHistory = redisTemplate.boundZSetOps("RANK_TODAY").score(classId);
        if (scoreHistory == null) {
            redisTemplate.boundZSetOps("RANK_TODAY").add(choose.getTitle()+"_"+classId, currentScore.doubleValue());
        }
        else
            redisTemplate.boundZSetOps("RANK_TODAY").add(choose.getTitle()+"_"+classId,scoreHistory+1);
        return choose;
    }

    Boolean ifExistInTags(String tag)
    {
        for(String tag_ : tags)
        {
            if(Objects.equals(tag_, tag))
                return true;
        }
        return false;
    }


    Boolean ifExistInDiffs(String diff)
    {
        for(String diff_ : diffs)
        {
            if(Objects.equals(diff_, diff))
                return true;
        }
        return false;
    }

    @Override
    public List<Show> getClassByMulTAG(String tag, String difficulty, String method, String target)
    {
        if(!ifExistInTags(tag) || !ifExistInDiffs(difficulty))
            return Collections.emptyList();
        return classDao.getShowByMulti(tag, difficulty, method, target);
    }

}

