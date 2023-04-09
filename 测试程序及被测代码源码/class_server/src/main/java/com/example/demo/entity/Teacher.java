package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @Column(name = "userid")
    private int userId;

    @Column(name = "classamount")
    private int classAmount;

    @Column(name = "nickname")
    private String nickName;

    private String image;
}