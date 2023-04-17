package com.yifan.bookstore.repository;

import com.yifan.bookstore.entry.tagNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagNodeRepository extends Neo4jRepository<tagNode, Integer> {
    @Query(value = "MATCH = (n:tagNode {name:$name}) RETURN n")
    tagNode findByName(String name);
    @Query(value = "MATCH (n:tagNode {name:$name})-[relation*0..2]->(result) return n,result")
    List<tagNode> findByRelatedTagNodeAndName(@Param("name")String name);
}
