package com.example.demo.entity;

import lombok.Data;


import javax.persistence.*;

@Data
@Entity
@Table(name = "class")
public class Class {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classId;

    @Column(name = "cocahid")
    private int teacherId;
    private String video;
    private String tag;
    private String title;
    private String image;
    private String method;
    private String difficulty;
    private String target;

    @OneToOne
    @JoinColumn(name = "cocahid", referencedColumnName = "userid", insertable = false, updatable = false)
    private Teacher teacher;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "classid", insertable = false, updatable = true)
    private ClassDetail classDetail;

    @Column(name = "totaltime")
    private int totalTime;

}