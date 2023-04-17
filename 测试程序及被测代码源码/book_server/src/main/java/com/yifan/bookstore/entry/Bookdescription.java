package com.yifan.bookstore.entry;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "description")
public class Bookdescription {
    @Field("Description")
    private String description;

    @Id
    @Field("_id")
    private int bookId;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}