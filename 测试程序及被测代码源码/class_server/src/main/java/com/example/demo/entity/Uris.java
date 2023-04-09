package com.example.demo.entity;

import lombok.Data;

@Data
public class Uris {
    private String className;
    private String method;
    private String dif;
    private String target;
    private String classUrl;
    private String imageUrl;
    private String teacherName;

    public Uris(String name, String cU, String tU)
    {
        this.className = name;
        this.classUrl = cU;
        this.imageUrl = tU;
    }

    public Uris(String name, String cU, String tU, String m, String d, String t, String tn)
    {
        this.className = name;
        this.classUrl = cU;
        this.imageUrl = tU;
        this.method = m;
        this.dif = d;
        this.target = t;
        this.teacherName = tn;
    }
}
