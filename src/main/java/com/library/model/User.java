package com.library.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String email;
    private final List<String> borrowedBooks = new ArrayList<>();

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String toString() {
        int n = borrowedBooks.size();
        return "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", borrowedBooks=" + borrowedBooks;
    }

}
