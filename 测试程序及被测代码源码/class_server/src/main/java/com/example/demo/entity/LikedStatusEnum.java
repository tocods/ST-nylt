package com.example.demo.entity;
import lombok.Getter;

/**
 * 用户点赞的状态
 */
@Getter
public enum LikedStatusEnum {
    LIKE(1, "收藏"),
    UNLIKE(0, "取消收藏/未收藏"),
    ;

    private Integer code;

    private String msg;

    LikedStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}