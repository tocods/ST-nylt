package com.example.demo.serviceimpl;

import com.example.demo.dao.ClassDao;
import com.example.demo.entity.Class;
import com.example.demo.entity.Show;
import com.example.demo.entity.Uris;
import com.example.demo.util.RedisKeyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassServerModuleTest {

    private final String[] tags = {"cycle", "run", "fit", "basketball","soccer"};

    @Mock
    private RedisTemplate mockRedisTemplate;
    @Mock
    private ClassDao mockClassDao;

    @InjectMocks
    private RedisServiceImpl redisServiceImplUnderTest;

    private ClassServerImpl classServerImplUnderTest;

    @BeforeEach
    void setUp() {
        classServerImplUnderTest = new ClassServerImpl();
        classServerImplUnderTest.classDao = mock(ClassDao.class);
        classServerImplUnderTest.redisTemplate = mock(RedisTemplate.class);
        redisServiceImplUnderTest.tagsIndex = mock(Hashtable.class);
    }

    @Test
    void testGetAllClasses() {
        Class aClass = new Class();
        aClass.setClassId(0);
        aClass.setTeacherId(0);
        aClass.setVideo("video");
        aClass.setTag("tag");
        aClass.setTitle("title");
        List<Class> expectedResult = List.of(aClass);

        Class aClass1 = new Class();
        aClass1.setClassId(0);
        aClass1.setTeacherId(0);
        aClass1.setVideo("video");
        aClass1.setTag("tag");
        aClass1.setTitle("title");
        List<Class> classes = List.of(aClass1);
        when(classServerImplUnderTest.classDao.getAllClasses()).thenReturn(classes);

        List<Class> result = classServerImplUnderTest.getAllClasses();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetClassesByTag() {
        Class aClass = new Class();
        aClass.setClassId(0);
        aClass.setTeacherId(0);
        aClass.setVideo("video");
        aClass.setTag("soccer");
        aClass.setTitle("title");
        List<Class> expectedResult = List.of(aClass);

        Class aClass1 = new Class();
        aClass1.setClassId(0);
        aClass1.setTeacherId(0);
        aClass1.setVideo("video");
        aClass1.setTag("soccer");
        aClass1.setTitle("title");
        List<Class> classes = List.of(aClass1);
        when(classServerImplUnderTest.classDao.getClassesByTag("soccer")).thenReturn(classes);

        // tag = tag
        List<Class> result = classServerImplUnderTest.getClassesByTag("tag");

        assertThat(result).isEqualTo(Collections.emptyList());

        // tag = soccer
        result = classServerImplUnderTest.getClassesByTag("soccer");
        assertThat(result).isEqualTo(expectedResult);

    }


    @Test
    void testGetClassesByTitle() {
        Class aClass = new Class();
        aClass.setClassId(0);
        aClass.setTeacherId(0);
        aClass.setVideo("video");
        aClass.setTag("tag");
        aClass.setTitle("title");
        List<Class> expectedResult = List.of(aClass);

        Class aClass1 = new Class();
        aClass1.setClassId(0);
        aClass1.setTeacherId(0);
        aClass1.setVideo("video");
        aClass1.setTag("tag");
        aClass1.setTitle("title");
        List<Class> classes = List.of(aClass1);
        when(classServerImplUnderTest.classDao.getClassesByTitle("title")).thenReturn(classes);

        List<Class> result = classServerImplUnderTest.getClassesByTitle("title");

        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void testGetClassById() {
        final Class expectedResult = new Class();
        expectedResult.setClassId(0);
        expectedResult.setTeacherId(0);
        expectedResult.setVideo("video");
        expectedResult.setTag("tag");
        expectedResult.setTitle("title");

        final Class aClass = new Class();
        aClass.setClassId(0);
        aClass.setTeacherId(0);
        aClass.setVideo("video");
        aClass.setTag("tag");
        aClass.setTitle("title");
        when(classServerImplUnderTest.classDao.getClassById(10, 0)).thenReturn(aClass);
        when(classServerImplUnderTest.classDao.getClassById(10, 1)).thenReturn(aClass);
        when(classServerImplUnderTest.classDao.getClassById(200, 1)).thenReturn(aClass);
        when(classServerImplUnderTest.classDao.getClassById(-1, 1)).thenReturn(null);

        BoundZSetOperations boundZSetOperations = mock(BoundZSetOperations.class);
        when(classServerImplUnderTest.redisTemplate.boundZSetOps("RANK_TODAY")).thenReturn(boundZSetOperations);

        // classId = -1, from = 1
        Class result = classServerImplUnderTest.getClassById(-1, 1);
        assertThat(result).isNull();

        // classId = 10, from = 0
        result = classServerImplUnderTest.getClassById(10, 0);
        assertThat(result).isEqualTo(aClass);

        when(classServerImplUnderTest.redisTemplate.boundZSetOps("RANK_TODAY").score(200)).thenReturn(null);
        when(classServerImplUnderTest.redisTemplate.boundZSetOps("RANK_TODAY").add("title_200", new BigDecimal(1+"."+(Integer.MAX_VALUE-200)).doubleValue())).thenReturn(null);

        // classId = 200, from = 1
        result = classServerImplUnderTest.getClassById(200, 1);
        assertThat(result).isEqualTo(aClass);

        when(classServerImplUnderTest.redisTemplate.boundZSetOps("RANK_TODAY").score(10)).thenReturn(0.0);
        when(classServerImplUnderTest.redisTemplate.boundZSetOps("RANK_TODAY").add("title_10", 1)).thenReturn(null);

        // classId = 10, from = 1
        result = classServerImplUnderTest.getClassById(10, 1);
        assertThat(result).isEqualTo(aClass);
    }

    @Test
    void testIfExistInTags() {
        assertThat(classServerImplUnderTest.ifExistInTags("soccer")).isTrue();
        assertThat(classServerImplUnderTest.ifExistInTags("tag")).isFalse();
    }

    @Test
    void testIfExistInDiffs() {
        assertThat(classServerImplUnderTest.ifExistInDiffs("low")).isTrue();
        assertThat(classServerImplUnderTest.ifExistInDiffs("diff")).isFalse();
    }

    @Test
    void testGetClassByMulTAG() {
        Show show = new Show();
        show.setClassId(0);
        show.setClassVideo("classVideo");
        show.setClassName("className");
        show.setClassTag("soccer");
        show.setTeacherName("teacherName");
        show.setDifficulty("low");
        show.setTarget("leg");
        List<Show> expectedResult = List.of(show);

        Show show1 = new Show();
        show1.setClassId(0);
        show1.setClassVideo("classVideo");
        show1.setClassName("className");
        show1.setClassTag("soccer");
        show1.setTeacherName("teacherName");
        show1.setDifficulty("low");
        show1.setTarget("leg");
        List<Show> shows = List.of(show1);
        when(classServerImplUnderTest.classDao.getShowByMulti("soccer", "low", "", "leg"))
                .thenReturn(shows);

        List<Show> result = classServerImplUnderTest.getClassByMulTAG("soccer", "low", "", "leg");

        assertThat(result).isEqualTo(expectedResult);

        assertThat(classServerImplUnderTest.getClassByMulTAG("soccer", "difficulty", "", "leg")).isEqualTo(Collections.emptyList());
        assertThat(classServerImplUnderTest.getClassByMulTAG("classTag", "low", "", "leg")).isEqualTo(Collections.emptyList());
    }


    @Test
    void testRedisServerGetClassById() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        SetOperations setOperations = mock(SetOperations.class);

        final Uris expectedResult = new Uris("name", "cU", "tU", "m", "d", "t", "tn");
        when(mockRedisTemplate.opsForValue()).thenReturn(valueOperations);


        final Uris uris = new Uris("name", "cU", "tU", "m", "d", "t", "tn");
        when(mockClassDao.getUris(200)).thenReturn(uris);

        when(mockRedisTemplate.opsForSet()).thenReturn(setOperations);

        // classId = 200(search = null)
        Uris result = redisServiceImplUnderTest.getClassById(200);
        assertThat(result).isEqualTo(expectedResult);

        // classId = -1
        result = redisServiceImplUnderTest.getClassById(-1);
        assertThat(result).isNull();

        // classId = 10(search != null)
        when(mockRedisTemplate.opsForValue().get("CLASS_ONE_10")).thenReturn(uris);
        result = redisServiceImplUnderTest.getClassById(10);
        assertThat(result).isEqualTo(uris);
    }



    @Test
    void testGetClassShow() {
        // page > max
        List<Show> result;
        result = redisServiceImplUnderTest.getClassShow(3000);
        assertThat(result).isEqualTo(Collections.emptyList());

        // page < 1
        result = redisServiceImplUnderTest.getClassShow(0);
        assertThat(result).isEqualTo(Collections.emptyList());


        // page = 50(fromRedis.size() != 0)
        HashOperations hashOperations = mock(HashOperations.class);
        when(mockRedisTemplate.opsForHash()).thenReturn(hashOperations);
        List<Show> expectedResult = new ArrayList<>();
        Integer len = tags.length;
        for(Integer i = 0; i < len; i++)
        {
            Show show = new Show();
            show.setClassId(0);
            show.setClassVideo("classVideo");
            show.setClassName("className");
            show.setClassTag(tags[i]);
            show.setCollect(false);
            List<Show> opsHashRet = List.of(show);
            expectedResult.addAll(opsHashRet);
            when(mockRedisTemplate.opsForHash().get(tags[i],"page"+String.valueOf(50))).thenReturn(opsHashRet);
            //hashOperations.put(tags[i], "page"+String.valueOf(50), opsHashRet);
        }
        result = redisServiceImplUnderTest.getClassShow(50);
        assertThat(result).isEqualTo(expectedResult);


        // page = 200(fromRedis.size() = 0)
        Show show1 = new Show();
        show1.setClassId(1990);
        show1.setClassVideo("classVideo");
        show1.setClassName("className");
        show1.setClassTag("classTag");
        show1.setCollect(false);
        List<Show> shows = List.of(show1);
        when(mockClassDao.getClassByPage(1990)).thenReturn(shows);


        result = redisServiceImplUnderTest.getClassShow(200);


        assertThat(result).isEqualTo(shows);
       // verify(mockRedisTemplate).setKeySerializer(any(RedisSerializer.class));
    }

    @Test
    void testGetClassShowByTag() {
        assertThat(redisServiceImplUnderTest.getClassShowByTag("classTag", 1, 1)).isEqualTo(Collections.emptyList());

        Show show = new Show();
        show.setClassId(0);
        show.setClassVideo("classVideo");
        show.setClassName("className");
        show.setClassTag("soccer");
        show.setCollect(false);
        List<Show> expectedResult = List.of(show);
        HashOperations hashOperations = mock(HashOperations.class);
        when(mockRedisTemplate.opsForHash()).thenReturn(hashOperations);
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(mockRedisTemplate.opsForValue()).thenReturn(valueOperations);


        // cacheNum = null, page = 10, fromRedis = null, ids = null
        when(mockRedisTemplate.opsForHash().get("CACHE","soccer10")).thenReturn(null);
        when(mockRedisTemplate.opsForValue().get("soccer_ids")).thenReturn(null);

        List<Show> result = redisServiceImplUnderTest.getClassShowByTag("soccer", 10, 1);
        assertThat(result).isEqualTo(null);

        // cacheNum = null, page = 0, fromRedis = null, ids = []
        when(mockRedisTemplate.opsForHash().get("CACHE","soccer0")).thenReturn(null);
        result = redisServiceImplUnderTest.getClassShowByTag("soccer", 0, 1);
        assertThat(result).isNull();

        // cacheNum = 50, page = 10, fromRedis != null, ids = []
        when(redisServiceImplUnderTest.tagsIndex.get("soccer")).thenReturn(50);
        when(mockRedisTemplate.opsForHash().get("CACHE","soccer10")).thenReturn(expectedResult);
        result = redisServiceImplUnderTest.getClassShowByTag("soccer", 10, 1);
        assertThat(result).isEqualTo(expectedResult);

        // cacheNum = 55, page = 6, fromRedis = null, ids = [0-39]
        when(redisServiceImplUnderTest.tagsIndex.get("soccer")).thenReturn(55);
        when(mockRedisTemplate.opsForHash().get("CACHE","soccer6")).thenReturn(null);
        when(mockRedisTemplate.opsForValue().get("soccer_ids")).thenReturn(Arrays.stream(IntStream.rangeClosed(0, 39).toArray()).boxed().collect(Collectors.toList()));
        result = redisServiceImplUnderTest.getClassShowByTag("soccer", 6, 1);
        assertThat(result).isNull();


        List<Show> expect1 =new ArrayList<>();
        List<Show> expect2 = new ArrayList<>();
        List<Show> shows = new ArrayList<>();
        when(mockRedisTemplate.opsForSet()).thenReturn(mock(SetOperations.class));
        for(Integer i = 0; i < 60; i ++)
        {
            Show show1 = new Show();
            show1.setClassId(i);
            show1.setClassVideo("classVideo");
            show1.setClassName("className");
            show1.setClassTag("soccer");
            show1.setCollect(false);
            if(i >= 40 && i < 50)
            {
                expect2.add(show1);
                when(mockRedisTemplate.opsForSet().isMember(RedisKeyUtils.MAP_KEY_USER_LIKED+1, i)).thenReturn(false);
            }
            if(i >= 50)
            {
                expect1.add(show1);
                when(mockRedisTemplate.opsForSet().isMember(RedisKeyUtils.MAP_KEY_USER_LIKED+1, i)).thenReturn(false);
            }
            shows.add(show1);
        }
        // cacheNum = 55, page = 6, fromRedis = null, ids = [0-49]
        when(mockRedisTemplate.opsForValue().get("soccer_ids")).thenReturn(Arrays.stream(IntStream.rangeClosed(0, 59).toArray()).boxed().collect(Collectors.toList()));
        when(mockClassDao.getClassFromIds(Arrays.stream(IntStream.rangeClosed(50, 59).toArray()).boxed().collect(Collectors.toList()))).thenReturn(expect1);
        result = redisServiceImplUnderTest.getClassShowByTag("soccer", 6, 1);
        assertThat(result).isEqualTo(expect1);

        // cacheNum = 50, page = 5, fromRedis = null, ids = [0-49]

        when(redisServiceImplUnderTest.tagsIndex.get("soccer")).thenReturn(50);
        when(mockRedisTemplate.opsForHash().get("PRECACHE","soccer")).thenReturn(shows);
        result = redisServiceImplUnderTest.getClassShowByTag("soccer", 5, 1);
        assertThat(result).isEqualTo(expect2);
    }
}
