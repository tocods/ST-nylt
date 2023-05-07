package com.example.demo.dao;

import com.example.demo.entity.*;
import com.example.demo.entity.Class;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassDao {
    List<Class> getAllClasses();
    List<Class> getClassesByTag(String tag);
    List<Class> getClassesByTitle(String title);
    List<Show> getClassByPage(int pid);
    Class getClassById(int classId, int from);
    List<Integer> getClassIdByTag(String tag);
    List<Show> getClassFromIds(List<Integer> ids);
    List<Show> getShowByMulti(String tag, String difficulty, String method, String target);
    Uris getUris(int classId);
}
