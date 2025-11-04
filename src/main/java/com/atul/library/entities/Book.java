package com.atul.library.entities;

import java.time.LocalDate;

public class Book {
    private String bookId;
    private String bookName;
    private String bookAuthor;
    private boolean borrowed;
    private String borrowedByUserId;
    private LocalDate borrowDate;
    private LocalDate expectedReturnDate;

    public Book(String bookId, String bookName, String bookAuthor) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.borrowed = false;
        this.borrowedByUserId = null;
    }

    // Getters & setters
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getBookAuthor() { return bookAuthor; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }

    public boolean isBorrowed() { return borrowed; }
    public void setBorrowed(boolean borrowed) { this.borrowed = borrowed; }

    public String getBorrowedByUserId() { return borrowedByUserId; }
    public void setBorrowedByUserId(String borrowedByUserId) { this.borrowedByUserId = borrowedByUserId; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getExpectedReturnDate() { return expectedReturnDate; }
    public void setExpectedReturnDate(LocalDate expectedReturnDate) { this.expectedReturnDate = expectedReturnDate; }

    @Override
    public String toString() {
        return bookId + " | " + bookName + " by " + bookAuthor +
                (borrowed ? " [Borrowed by: " + borrowedByUserId + "]" : " [Available]");
    }
}
