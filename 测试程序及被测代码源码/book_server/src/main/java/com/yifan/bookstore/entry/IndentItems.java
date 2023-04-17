package com.yifan.bookstore.entry;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;


@Entity
@Table(name="indentitems")
public class IndentItems {


    private int bookId;
    private String bookname;
    private String bookauthor;
    private String booktype;
    private double bookprice;
    private String username;
    private int indentId;
    private int amount;
    @Id
    @GeneratedValue
    private int pk;


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookauthor() {
        return bookauthor;
    }

    public void setBookauthor(String bookauthor) {
        this.bookauthor = bookauthor;
    }

    public String getBooktype() {
        return booktype;
    }

    public void setBooktype(String booktype) {
        this.booktype = booktype;
    }

    public void setBookprice(double bookprice) {
        this.bookprice = bookprice;
    }

    public double getBookprice() {
        return bookprice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIndentId() {
        return indentId;
    }

    public void setIndentId(int indentId) {
        this.indentId = indentId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
