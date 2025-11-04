package com.atul.library;

import com.atul.library.misc.LibraryDatabase;
import com.atul.library.services.AuthService;
import com.atul.library.services.LibraryService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryDatabase.loadData();

        AuthService auth = new AuthService();
        LibraryService library = new LibraryService(auth);
        Scanner sc = new Scanner(System.in);

        boolean running = true;

        while (running) {
            if (auth.getCurrentUser() == null) {
                System.out.println("\n--- Library System ---");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("0. Exit");
                System.out.print("Choose: ");
                String choice = sc.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Username: "); String u = sc.nextLine();
                        System.out.print("Password: "); String p = sc.nextLine();
                        auth.login(u, p);
                        break;
                    case "2":
                        System.out.print("Username: "); String ru = sc.nextLine();
                        System.out.print("Password: "); String rp = sc.nextLine();
                        auth.register(ru, rp);
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("\n--- Welcome " + auth.getCurrentUser().getUsername() + " ---");
                System.out.println("1. List Books");
                System.out.println("2. Borrow Book");
                System.out.println("3. Return Book");
                System.out.println("4. Logout");
                System.out.print("Choose: ");
                String choice = sc.nextLine();

                switch (choice) {
                    case "1":
                        library.listBooks();
                        break;
                    case "2":
                        System.out.print("Enter Book ID to borrow: ");
                        String bid = sc.nextLine();
                        System.out.print("Enter duration in weeks (1-4, or 12 for 3 months): ");
                        int weeks = Integer.parseInt(sc.nextLine());
                        library.borrowBook(bid, weeks);
                        break;
                    case "3":
                        System.out.print("Enter Book ID to return: ");
                        String rid = sc.nextLine();
                        library.returnBook(rid);
                        break;
                    case "4":
                        auth.logout();
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }

        sc.close();
        System.out.println("Exiting Library System...");
    }
}
