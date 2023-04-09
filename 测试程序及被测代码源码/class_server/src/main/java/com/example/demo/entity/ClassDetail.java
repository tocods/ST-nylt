package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "classdetail")
public class ClassDetail {
    @Id
    @Column(name = "classid")
    private int classId;

    @Column(name = "viewcounts")
    private int viewCounts;

    private int likes;
}