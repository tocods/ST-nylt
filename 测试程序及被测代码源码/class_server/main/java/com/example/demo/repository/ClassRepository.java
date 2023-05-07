package com.example.demo.repository;

import com.example.demo.entity.*;
import com.example.demo.entity.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class, Integer>, JpaSpecificationExecutor<Class> {
    @Query(value = "select c from Class c")
    List<Class> getAllClasses();

    @Query(value = "from Class where tag = :tag")
    List<Class> getClassesByTag(@Param("tag") String tag);

    @Query(value = "from Class where id = :id")
    Class getClassById(@Param("id")int classId);

    @Query(value = "from Class where title = :title")
    List<Class> getClassByTitle(@Param("title") String title);

   // @Query(nativeQuery = true,value = "select * from Class where id between :sid and :eid")
    @Query(value = "select new com.example.demo.entity.Show(c.id,c.video,c.title,c.tag,t.nickName,c.image,c.difficulty,c.target,c.method,c.totalTime) "+
           "from Class c left join Teacher t on c.teacherId=t.userId where c.id between :sid and :eid")
    List<Show> getClassByPage(@Param("sid")int startId, @Param("eid")int endId);

    @Query(value = "select id from Class c where c.tag =:tag")
    List<Integer> getClassIdByTag(@Param("tag")String tag);


    @Query(value = "select new com.example.demo.entity.Show(c.classId,c.video,c.title,c.tag,t.nickName,c.image,c.difficulty,c.target,c.method,c.totalTime) " +
            "from Class c left join Teacher t on c.teacherId=t.userId where c.classId in :ids")
    List<Show> getClassFromIds(@Param("ids")List<Integer> ids);

    @Query(value = "select new com.example.demo.entity.Show(c.classId,c.video,c.title,c.tag,t.nickName,c.image,c.difficulty,c.target,c.method,c.totalTime) " +
            "from Class c left join Teacher t on c.teacherId=t.userId where (:tag = null or c.tag = :tag) and (:dif = null or c.difficulty = :dif) and" +
            "(:method = null or c.method = :method) and (:target = null or c.target = :target)")
    List<Show> getShowsFromMulti(@Param("tag")String tag, @Param("dif")String difficulty, @Param("method")String method, @Param("target")String target);

    @Query(value = "select new com.example.demo.entity.Uris(c.title,c.video,c.image, c.method, c.difficulty,c.target,t.nickName) from Class c left join Teacher t on c.teacherId=t.userId where c.classId = :classId")
    Uris getUris(@Param("classId")int classId);

}