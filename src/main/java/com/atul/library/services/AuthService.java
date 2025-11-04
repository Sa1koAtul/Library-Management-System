package com.atul.library.services;

import com.atul.library.entities.User;
import com.atul.library.misc.LibraryDatabase;

public class AuthService {
    private User currentUser;

    public boolean login(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("Username or password are empty!");
            return false;
        }

        for (User u : LibraryDatabase.getUsers()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                currentUser = u;
                System.out.println("User logged in successfully!");
                return true;
            }
        }
        System.out.println("Invalid credentials.");
        return false;
    }

    public boolean register(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("Username or password are empty!");
            return false;
        }

        for (User u : LibraryDatabase.getUsers()) {
            if (u.getUsername().equals(username)) {
                System.out.println(username + " is already registered!");
                return false;
            }
        }

        User newUser = new User();
        newUser.setUserId(LibraryDatabase.generateUserId());
        newUser.setUsername(username);
        newUser.setPassword(password);
        LibraryDatabase.addUser(newUser);

        System.out.println("User registered successfully!");
        return true;
    }

    public void logout() {
        currentUser = null;
        System.out.println("Logged out successfully!");
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
