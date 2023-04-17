package com.yifan.bookstore.entry;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "tagNode")
public class tagNode {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    public String getName() {
        return name;
    }
    @Relationship(type = "relation")
    public Set<tagNode> relatedTagNode = new HashSet<>();;
}
