package com.example.demo.serviceimpl;

import com.example.demo.dao.ClassDao;
import com.example.demo.entity.Collect;
import com.example.demo.entity.LikedStatusEnum;
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
    private static String CLASS_REDIS_TAG = "CLASS_ONE_";
    private static String PAGE_REDIS_TAG = "CLASS_PAGE_";
    private final String[] tags = {"cycle", "run", "fit", "basketball","soccer"};
    private final Hashtable<String,Integer> tagsIndex = new Hashtable<>();

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ClassDao classDao;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Uris getClassById(int classId)
    {
       Uris search = (Uris) redisTemplate.opsForValue().get(CLASS_REDIS_TAG+classId);
       if(search == null)
       {
           search = getClassByIdFromSql(classId);
       }
       BoundZSetOperations zSetOperations = redisTemplate.boundZSetOps("RANK_TODAY");
       zSetOperations.incrementScore(search.getClassName()+"_"+classId,1);
       BoundZSetOperations zSetOperations1 = redisTemplate.boundZSetOps("RANK");
       zSetOperations1.incrementScore(search.getClassName()+"_"+classId,1);
       return search;
    }

    private Uris getClassByIdFromSql(int classId)
    {
        Uris uris = classDao.getUris(classId,1);
        redisTemplate.opsForSet().add(CLASS_REDIS_TAG+classId,uris);
        return uris;
    }

    @Override
    public List<Collect> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<Collect> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            String[] split = key.split("::");
            String likedClassId = split[0];
            String likedPostId = split[1];
            Integer value = (Integer) entry.getValue();
            Collect userLike = new Collect(likedClassId, likedPostId, value);
            list.add(userLike);
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        }
        Set keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
        return list;
    }

    @Override
    public void getIds()
    {
        int now = 0;
        for(String tag: tags)
        {
            List<Integer> ids = classDao.getClassIdByTag(tag);
            redisTemplate.opsForValue().set(tag+"_ids",ids);
            int cacheNum = PageUtils.tagSize * PageUtils.cachePage - 1;
            if(cacheNum >= ids.size())
                cacheNum = ids.size();
            System.out.println(cacheNum+"_"+tag);
            tagsIndex.put(tag,cacheNum);
            System.out.println(tagsIndex.get(tag)+"_tag"+tag);
            List<Show> caches = classDao.getClassFromIds(ids.subList(0,cacheNum-1));
            redisTemplate.opsForHash().put("PRECACHE",tag, caches);
        }

    }


    @Override
    public List<Show> getClassShow(int page)
    {
        if(page > PageUtils.pageMax)
            return null;
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int len = tags.length;
        List<Show> fromRedis = new ArrayList<>();
        for (int i = 0; i < len; i++)
        {
            List<Show> tmp = (List<Show>) redisTemplate.opsForHash().get(tags[i],"page"+String.valueOf(page));
            if(tmp != null)
                fromRedis.addAll(tmp);
        }
        if(fromRedis.size() == 0)
        {
            int startId = (page - 1) * PageUtils.pageSize;
            List<Show> fromMySql = classDao.getClassByPage(startId);
            int length = fromMySql.size();
            HashMap<String,List<Show>> results = new HashMap<>();
            for (int i = 0; i < length; i++)
            {
                List<Show> now = results.get(fromMySql.get(i).getClassTag());
                if(now == null)
                {
                    now = new ArrayList<>();
                }
                now.add(fromMySql.get(i));
                results.put(fromMySql.get(i).getClassTag(),now);
            }
            for(Map.Entry<String,List<Show>> cur: results.entrySet())
            {
                redisTemplate.opsForHash().put(cur.getKey(),"page"+String.valueOf(page),cur.getValue());
            }
            return fromMySql;
        }
        return fromRedis;
    }

    @Override
    public List<Show> getClassShowByTag(String tag, int page, int userId)
    {
        Integer cacheNum = tagsIndex.get(tag);
        System.out.println(cacheNum+"");
        //比如一共10条缓存，即1页，cacheNum=9,那么加入page=1,此时0<10,10
        if(cacheNum == null || (page - 1) * PageUtils.tagSize >= cacheNum || page * PageUtils.tagSize > cacheNum)
            return  getFromNoCache(tag,page,userId);
        else
            return getFromCache(tag, page,userId);
    }

    private List<Show> getFromNoCache(String tag, int page, int userId)
    {
        List<Show> fromRedis = (List<Show>) redisTemplate.opsForHash().get("CACHE",tag+String.valueOf(page));
        if(fromRedis == null || fromRedis.size() == 0)
        {
            List<Integer> ids = (List<Integer>)redisTemplate.opsForValue().get(tag+"_ids");
            int startId = (page - 1) * PageUtils.tagSize;
            if(startId >= ids.size())
            {
                System.out.println("page"+page+"超过,该类别下一共"+ids.size()+"课程");
                return null;
            }
            int lastId = page * PageUtils.tagSize;
            if(lastId >= ids.size())
                lastId = ids.size();
            if(ids == null || ids.size() == 0)
            {
                return null;
            }
            System.out.println(ids.subList(startId,lastId));
            List<Show> fromMysql = classDao.getClassFromIds(ids.subList(startId,lastId));
            redisTemplate.opsForHash().put("CACHE", tag+String.valueOf(page), fromMysql);
            for (Show e:
                 fromMysql) {
                e.setCollect(this.isMember(e.getClassId(),userId));
            }
            return fromMysql;
        }
        return fromRedis;
    }

    private List<Show> getFromCache(String tag, int page, int userId)
    {
        List<Show> ret = ((List<Show>)redisTemplate.opsForHash().get("PRECACHE",tag)) .subList((page-1)*PageUtils.tagSize, page*PageUtils.tagSize);
        for (Show e:
             ret) {
            e.setCollect(this.isMember(e.getClassId(), userId));
        }
        return ret;
    }

    @Override
    public String doCollect(int postid, int userid)
    {
        String result = "";
        try {
            String key = RedisKeyUtils.MAP_KEY_CLASS_LIKED+postid;
            long object = redisTemplate.opsForSet().add(key,userid);
            redisTemplate.opsForSet().add(RedisKeyUtils.MAP_KEY_USER_LIKED+userid,postid);
            if(object == 1)
            {
                result="success";
            }
            else
            {
                result="fail";
            }
        }catch (Exception ex)
        {
            log.error("exception:"+ex);
        }
        return result;
    }

    @Override
    public String undoCollect(int postid, int userid)
    {
        String result = "";
        try {
            String key = RedisKeyUtils.MAP_KEY_CLASS_LIKED+postid;
            long object = redisTemplate.opsForSet().remove(key,userid);
            redisTemplate.opsForSet().remove(RedisKeyUtils.MAP_KEY_USER_LIKED+userid,postid);
            redisTemplate.opsForSet().add(RedisKeyUtils.MAP_KEY_USER_UNLIKED+userid,postid);
            if(object == 1)
            {
                result="success";
            }
            else
            {
                result="fail";
            }
        }catch (Exception ex)
        {
            log.error("exception:"+ex);
        }
        return result;
    }

    @Override
    public boolean isMember(int postid, int userid)
    {
        return redisTemplate.opsForSet().isMember(RedisKeyUtils.MAP_KEY_USER_LIKED+userid,postid);
    }

    @Override
    public List<Integer> collects(int userid)
    {
        return new ArrayList<>(redisTemplate.opsForSet().members(RedisKeyUtils.MAP_KEY_USER_LIKED+userid));
    }
}
