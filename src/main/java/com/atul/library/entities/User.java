package com.atul.library.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String username;
    private String password;
    private List<Book> borrowedBooks = new ArrayList<>();

    public User(String username, String password, String userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public User() {}

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Book> getBorrowedBooks() { return borrowedBooks; }
    public void setBorrowedBooks(List<Book> borrowedBooks) { this.borrowedBooks = borrowedBooks; }

    public void borrowBook(Book book) { borrowedBooks.add(book); }
    public void returnBook(Book book) { borrowedBooks.remove(book); }

    @Override
    public String toString() {
        return userId + " | " + username;
    }
}
