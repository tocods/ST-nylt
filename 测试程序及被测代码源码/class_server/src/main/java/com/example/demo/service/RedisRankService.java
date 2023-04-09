package com.example.demo.service;

import com.example.demo.entity.Rank;

import java.util.List;

public interface RedisRankService {
    void clearDay();
    List<Rank> listRank();
    List<Rank> listRankToday();
}
