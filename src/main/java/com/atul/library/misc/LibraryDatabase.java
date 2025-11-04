package com.atul.library.misc;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.atul.library.entities.Book;
import com.atul.library.entities.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LibraryDatabase {

    private static final String DB_FILE = "localDb/library.json"; // path in resources
    private static List<User> users = new ArrayList<>();
    private static List<Book> books = new ArrayList<>();

    private static int userIdCounter = 1;
    private static int bookIdCounter = 1;

    // --------- JSON load/save ---------
    public static void loadData() {
        try {
            Gson gson = buildGson();

            URL resource = LibraryDatabase.class.getClassLoader().getResource(DB_FILE);
            if (resource == null) {
                throw new RuntimeException("Database file not found at: " + DB_FILE);
            }

            Reader reader = new FileReader(Paths.get(resource.toURI()).toFile());

            Type dbType = new TypeToken<DatabaseWrapper>() {}.getType();
            DatabaseWrapper db = gson.fromJson(reader, dbType);

            users = db.users != null ? db.users : new ArrayList<>();
            books = db.books != null ? db.books : new ArrayList<>();

            // Update ID counters based on existing data
            updateIdCounters();

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveData() {
        try {
            Gson gson = buildGson();
            DatabaseWrapper db = new DatabaseWrapper();
            db.users = users;
            db.books = books;

            FileWriter writer = new FileWriter("src/main/resources/" + DB_FILE);
            gson.toJson(db, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Gson buildGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                    @Override
                    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE));
                    }
                })
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
                    }
                })
                .create();
    }

    private static void updateIdCounters() {
        // Users
        for (User u : users) {
            try {
                int idNum = Integer.parseInt(u.getUserId().substring(1));
                if (idNum >= userIdCounter) userIdCounter = idNum + 1;
            } catch (Exception ignored) {}
        }

        // Books
        for (Book b : books) {
            try {
                int idNum = Integer.parseInt(b.getBookId().substring(1));
                if (idNum >= bookIdCounter) bookIdCounter = idNum + 1;
            } catch (Exception ignored) {}
        }
    }

    // --------- User methods ---------
    public static List<User> getUsers() { return users; }
    public static void addUser(User user) {
        users.add(user);
        saveData();
    }
    public static String generateUserId() { return "U" + userIdCounter++; }

    // --------- Book methods ---------
    public static List<Book> getBooks() { return books; }
    public static void addBook(Book book) {
        books.add(book);
        saveData();
    }

    public static Book getBookById(String id) {
        for (Book book : books) {
            if (book.getBookId().equals(id)) return book;
        }
        return null;
    }

    public static void listBooks() {
        System.out.println("---- Library Books ----");
        for (Book b : books) {
            System.out.printf("%s | %s | %s | Borrowed: %s\n",
                    b.getBookId(), b.getBookName(), b.getBookAuthor(),
                    b.isBorrowed() ? "Yes" : "No");
        }
    }

    // --------- Wrapper class for JSON mapping ---------
    private static class DatabaseWrapper {
        List<User> users = new ArrayList<>();
        List<Book> books = new ArrayList<>();
    }
}
