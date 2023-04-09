package com.example.demo.entity;

import lombok.Data;

@Data
public class Caentity {
    private Integer id;
    private String difficulty;
    private String target;
    private String method;

    public Caentity(Integer id, String difficulty, String target, String method)
    {
        this.id = id;
        this.difficulty = difficulty;
        this.target = target;
        this.method = method;
    }

    public Caentity(){}
}
