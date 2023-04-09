package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.entity.Class;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ClassServer {
    List<Class> getAllClasses();
    List<Class> getClassesByTag(String tag);
    List<Class> getClassesByTitle(String title);
    Class getClassById(int classId, int from);
    Class getClassForCollect(int classId);
    List<Show> getClassFromIds(List<Integer> ids);
    List<Show> getClassByMulTAG(String tag, String difficulty, String method, String target);
}
