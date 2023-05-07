package com.example.demo.repository;

import com.example.demo.entity.ClassDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;

import java.util.List;

public interface ClassDetailRepository extends JpaRepository<ClassDetail, Integer> {

}