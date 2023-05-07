package com.example.demo.serviceimpl;

import com.example.demo.dao.ClassDao;
import com.example.demo.entity.Show;
import com.example.demo.entity.Uris;
import com.example.demo.service.RedisService;
import com.example.demo.util.PageUtils;
import com.example.demo.util.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    static String CLASS_REDIS_TAG = "CLASS_ONE_";
    static String PAGE_REDIS_TAG = "CLASS_PAGE_";
    final String[] tags = {"cycle", "run", "fit", "basketball","soccer"};
    Hashtable<String,Integer> tagsIndex = new Hashtable<>();

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ClassDao classDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Uris getClassById(int classId) {
        if(classId < 0)
            return null;

       Uris search = (Uris) redisTemplate.opsForValue().get(CLASS_REDIS_TAG+classId);
       if(search == null)
         search = getClassByIdFromSql(classId);
       return search;
    }

    private Uris getClassByIdFromSql(int classId) {
        Uris uris = classDao.getUris(classId);
        redisTemplate.opsForSet().add(CLASS_REDIS_TAG+classId,uris);
        return uris;
    }

    @Override
    public List<Show> getClassShow(int page) {
        if(page > PageUtils.pageMax || page < 1)
            return Collections.emptyList();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int len = tags.length;
        List<Show> fromRedis = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            List<Show> tmp = (List<Show>) redisTemplate.opsForHash().get(tags[i],"page"+String.valueOf(page));
            if(tmp != null)
                fromRedis.addAll(tmp);
        }
        if(fromRedis.size() == 0) {
            int startId = (page - 1) * PageUtils.pageSize;
            List<Show> fromMySql = classDao.getClassByPage(startId);
            int length = fromMySql.size();
            HashMap<String,List<Show>> results = new HashMap<>();
            for (int i = 0; i < length; i++) {
                List<Show> now = results.get(fromMySql.get(i).getClassTag());
                if(now == null)
                    now = new ArrayList<>();
                now.add(fromMySql.get(i));
                results.put(fromMySql.get(i).getClassTag(),now);
            }
            for(Map.Entry<String,List<Show>> cur: results.entrySet())
                redisTemplate.opsForHash().put(cur.getKey(),"page"+String.valueOf(page),cur.getValue());
            return fromMySql;
        }
        return fromRedis;
    }

    Boolean ifExistInTags(String tag) {
        for(String tag_ : tags) {
            if(Objects.equals(tag_, tag))
                return true;
        }
        return false;
    }


    @Override
    public List<Show> getClassShowByTag(String tag, int page, int userId) {
        if(!ifExistInTags(tag))
            return Collections.emptyList();
        Integer cacheNum = tagsIndex.get(tag);
        if(cacheNum == null || (page - 1) * PageUtils.tagSize >= cacheNum || page * PageUtils.tagSize > cacheNum)
            return  getFromNoCache(tag,page,userId);
        else
            return getFromCache(tag, page,userId);
    }

    private List<Show> getFromNoCache(String tag, int page, int userId) {
        List<Show> fromRedis = (List<Show>) redisTemplate.opsForHash().get("CACHE",tag+String.valueOf(page));
        if(fromRedis != null && fromRedis.size() > 0)
            return fromRedis;
        List<Integer> ids = (List<Integer>)redisTemplate.opsForValue().get(tag+"_ids");
        int startId = (page - 1) * PageUtils.tagSize;
        if(ids == null || ids.size() == 0 || startId >= ids.size())
            return null;
        int lastId = page * PageUtils.tagSize;
        if(lastId >= ids.size())
            lastId = ids.size();
        System.out.println(ids.subList(startId,lastId));
        List<Show> fromMysql = classDao.getClassFromIds(ids.subList(startId,lastId));
        redisTemplate.opsForHash().put("CACHE", tag+String.valueOf(page), fromMysql);
        for (Show e: fromMysql)
            e.setCollect(redisTemplate.opsForSet().isMember(RedisKeyUtils.MAP_KEY_USER_LIKED+userId, e.getClassId()));
        return fromMysql;
    }

    private List<Show> getFromCache(String tag, int page, int userId) {
        List<Show> ret = ((List<Show>)redisTemplate.opsForHash().get("PRECACHE",tag)) .subList((page-1)*PageUtils.tagSize, page*PageUtils.tagSize);
        for (Show e: ret)
            e.setCollect(redisTemplate.opsForSet().isMember(RedisKeyUtils.MAP_KEY_USER_LIKED+userId, e.getClassId()));
        return ret;
    }
}
