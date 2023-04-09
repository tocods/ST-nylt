package com.example.demo.entity;

import lombok.Data;

@Data
public class Show {
    private Integer classId;
    private String classVideo;
    private String className;
    private String classTag;
    private String teacherName;
    private int totalTime;
    private String classImage;
    private String difficulty;
    private String target;
    private String method;
    private boolean collect;

    public Show(Integer id, String video, String cname, String tag,
                String tname, String classImage, String difficulty,
                String target, String method, int totalTime)
    {
        this.classId = id;
        this.classVideo = video;
        this.className = cname;
        this.classTag = tag;
        this.teacherName = tname;
        this.classImage = classImage;
        this.difficulty = difficulty;
        this.method = method;
        this.target = target;
        this.totalTime = totalTime;
    }

    public Show(){}
}
