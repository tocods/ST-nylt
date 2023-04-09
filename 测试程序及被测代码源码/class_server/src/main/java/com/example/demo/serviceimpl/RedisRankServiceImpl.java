package com.example.demo.serviceimpl;

import com.example.demo.entity.Class;
import com.example.demo.entity.Rank;
import com.example.demo.entity.Uris;
import com.example.demo.service.ClassServer;
import com.example.demo.service.RedisRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RedisRankServiceImpl implements RedisRankService {

    private static String CLASS_REDIS_TAG = "CLASS_ONE_";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ClassServer classServer;

    private List<Rank> ranks = new ArrayList<>();

    private List<Rank> ranksToday = new ArrayList<>();



    @Override
    public List<Rank> listRank()
    {
        BoundZSetOperations zSetOps = redisTemplate.boundZSetOps("RANK");
        int last = 19;
        if(zSetOps.size() <= 20)
            last = (int)(zSetOps.size()-1);
        Set<String> ranks =  zSetOps.reverseRange(0, last);
        List<Rank> ret = new ArrayList<>();
        for(String rank : ranks)
        {
            String[] dil = rank.split("_");
            String name = dil[0];
            String id = dil[1];
            Uris uris = (Uris) redisTemplate.opsForValue().get(CLASS_REDIS_TAG+id);
            
            //ret.add(new Rank(Integer.parseInt(id), 0, name, uris.getTeacherName(), uris.getImageUrl(), uris.getMethod(),uris.getDif(), uris.getTarget()));
        }
        return ret;
    }

    @Override
    public List<Rank> listRankToday()
    {
        BoundZSetOperations zSetOps = redisTemplate.boundZSetOps("RANK_TODAY");
        int last = 19;
        if(zSetOps.size() <= 20)
            last = (int)(zSetOps.size()-1);
        Set<String> ranks =  zSetOps.reverseRange(0, last);
        List<Rank> ret = new ArrayList<>();
        for(String rank : ranks)
        {
            String[] dil = rank.split("_");
            String name = dil[0];
            String id = dil[1];
            //ret.add(new Rank(Integer.parseInt(id), 0, name));
            Uris uris = (Uris) redisTemplate.opsForValue().get(CLASS_REDIS_TAG+id);

            //ret.add(new Rank(Integer.parseInt(id), 0, name, uris.getTeacherName(), uris.getImageUrl(), uris.getMethod(),uris.getDif(), uris.getTarget()));
        }
        return ret;
    }

    @Override
    public void clearDay()
    {
        BoundZSetOperations zSetOps = redisTemplate.boundZSetOps("RANK_TODAY");
        zSetOps.reverseRange(0,-1);
        zSetOps.remove("RANK_TODAY");
    }
}
