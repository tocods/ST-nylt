package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "collect")
public class Collect {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userid")
    private int userId;

    @Column(name = "classid")
    private int classId;

    @Column(name = "createdate")
    private Timestamp createDate;

    @Column(name = "updatedate")
    private Timestamp updateDate;

    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    @OneToOne
    @JoinColumn(name = "classid", referencedColumnName = "id", insertable = false,updatable = false)
    private Class collectClass;

    public Collect(){}

    public Collect(String likedClassId, String likedPostId, Integer status)
    {
        this.classId = Integer.parseInt(likedClassId);
        this.userId = Integer.parseInt(likedPostId);
        this.status = status;
    }
}