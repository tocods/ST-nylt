package com.yifan.bookstore.entry;

import org.hibernate.validator.constraints.Length;
import org.apache.solr.client.solrj.beans.Field;
import javax.persistence.*;


@Entity
@Table(name="book")
public class Book {
    @Id
    @Field(value="bookid")
    private int id;
    private int isbn;
    @Field(value="name")
    private String name;
    private String type;
    @Field(value="author")
    private String author;
    private double price;
    private int inventory;
    @Field(value="description")
    private String description;
private String image;


    public String getType() {
        return type;
    }

    public void setType(String category) {
        this.type = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int bookId) {
        this.id = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String bookName) {
        this.name = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price =price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}