package com.example.demo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class Rank {
    private Integer classID;
    private Integer classView;
    private String className;
    private String teacherName;
    private String classImage;
    private String method;
    private String dif;
    private String target;


    public Rank(Integer id, Integer view, String name)
    {
        this.classID = id;
        this.classView = view;
        this.className = name;
    }

    /*public Rank(Integer id, Integer view, String class_name, String teacher_name, String class_image, String m, String d, String t)
    {
        this.classID = id;
        this.classView = view;
        this.className = class_name;
        this.teacherName = teacher_name;
        this.classImage = class_image;
        this.method = m;
        this.dif = d;
        this.target = t;
    }*/


    public Rank(Integer id, String class_name, String teacher_name,String class_image)
    {
        this.classID = id;
        this.className = class_name;
        this.teacherName = teacher_name;
        this.classImage = class_image;
    }

    public Rank(){}
}
