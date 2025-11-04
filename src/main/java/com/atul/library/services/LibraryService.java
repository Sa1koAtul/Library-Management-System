package com.atul.library.services;

import com.atul.library.entities.Book;
import com.atul.library.entities.User;
import com.atul.library.misc.LibraryDatabase;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LibraryService {

    private final AuthService authService;

    public LibraryService(AuthService authService) {
        this.authService = authService;
    }

    // Borrow a book for a given duration in weeks
    public boolean borrowBook(String bookId, int weeks) {
        User user = authService.getCurrentUser();
        if (user == null) {
            System.out.println("Login first to borrow books.");
            return false;
        }

        Book book = LibraryDatabase.getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }
        if (book.isBorrowed()) {
            System.out.println("Book already borrowed.");
            return false;
        }

        LocalDate today = LocalDate.now();
        book.setBorrowed(true);
        book.setBorrowedByUserId(user.getUserId());
        book.setBorrowDate(today);
        book.setExpectedReturnDate(today.plusWeeks(weeks));

        user.borrowBook(book);
        LibraryDatabase.saveData();

        System.out.println("Book borrowed: " + book.getBookName() + " | Return by: " + book.getExpectedReturnDate());
        return true;
    }

    // Return a book and calculate fines
    public boolean returnBook(String bookId) {
        User user = authService.getCurrentUser();
        if (user == null) {
            System.out.println("Login first to return books.");
            return false;
        }

        Book book = LibraryDatabase.getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }
        if (!book.isBorrowed() || !book.getBorrowedByUserId().equals(user.getUserId())) {
            System.out.println("This book is not borrowed by you.");
            return false;
        }

        LocalDate today = LocalDate.now();
        long overdueDays = 0;
        if (today.isAfter(book.getExpectedReturnDate())) {
            overdueDays = ChronoUnit.DAYS.between(book.getExpectedReturnDate(), today);
        }

        book.setBorrowed(false);
        book.setBorrowedByUserId(null);
        book.setBorrowDate(null);
        book.setExpectedReturnDate(null);

        user.returnBook(book);
        LibraryDatabase.saveData();

        if (overdueDays > 0) {
            long fine = overdueDays * 5;
            System.out.println("Book returned late by " + overdueDays + " days. Fine = Rs " + fine);
        } else {
            System.out.println("Book returned on time. Thank you!");
        }
        return true;
    }

    public void listBooks() {
        LibraryDatabase.listBooks();
    }
}
