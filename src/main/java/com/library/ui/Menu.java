package com.library.ui;

import com.library.exception.*;
import com.library.manager.Library;
import com.library.model.Book;
import com.library.model.User;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Library library = new Library();
    static Scanner scanner = new Scanner(System.in);

        public void start() {
            boolean running = true;

            while (running) {

                displayMenu();

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> removeBook();
                    case 3 -> findBooksByIsbn();
                    case 4 -> findBooksByTitle();
                    case 5 -> findBooksByAuthor();
                    case 6 -> findBooksByPublishYear();
                    case 7 -> addUser();
                    case 8 -> removeUser();
                    case 9 -> findUserById();
                    case 10 -> findUsersByName();
                    case 11 -> findUsersByEmail();
                    case 12 -> borrowBook();
                    case 13 -> returnBook();
                    case 14 -> showAllBooks();
                    case 15 -> showAllUsers();
                    case 16 -> {

                        System.out.println("Exiting the system. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Wrong selection, please retry!");
                }
            }
            scanner.close();
        }

    public static void displayMenu() {
        System.out.println("Please select an operation:" +
                "\n1. Add Book" +
                "\n2. Remove Book" +
                "\n3. Search Book by ISBN" +
                "\n4. Search Book by Title" +
                "\n5. Search Book by Author" +
                "\n6. Search Book by Publish Year" +
                "\n7. Add User" +
                "\n8. Remove User" +
                "\n9. Search User by ID" +
                "\n10. Search User by Name" +
                "\n11. Search User by Email" +
                "\n12. Borrow Book" +
                "\n13. Return Book" +
                "\n14. Show All Books" +
                "\n15. Show All Users" +
                "\n16. Exit");
    }

    public static void addBook() {
        System.out.println("Enter book ISBN:");
        String isbn = scanner.nextLine();

        System.out.println("Enter book title:");
        String title = scanner.nextLine();

        System.out.println("Enter book author:");
        String author = scanner.nextLine();

        System.out.println("Enter book publish year:");
        int publishYear = scanner.nextInt();

        Book book = new Book(isbn, title, author, publishYear);
        try {
            library.addBook(book);
        } catch (DuplicateBookException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void removeBook() {
        System.out.println("Enter book ISBN:");
        String isbn = scanner.nextLine();
        try {
            library.removeBook(isbn);
        } catch (BookNotFoundException | BookBorrowedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void findBooksByIsbn() {
        System.out.println("Enter book ISBN:");
        String isbn = scanner.nextLine();
        try {
            System.out.println(library.findBookByIsbn(isbn));
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void findBooksByTitle() {
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        try {
            System.out.println(library.findBooksByTitle(title));
        } catch(BookNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void findBooksByAuthor() {
        System.out.println("Enter book author:");
        String author = scanner.nextLine();
        try {
            System.out.println(library.findBooksByAuthor(author));
        } catch (BookNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void findBooksByPublishYear() {
        System.out.println("Enter book publish year:");
        int publishYear = scanner.nextInt();
        try {
            System.out.println(library.findBooksByPublishYear(publishYear));
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void addUser() {
        System.out.println("Enter user ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter user name:");
        String name = scanner.nextLine();

        System.out.println("Enter user email:");
        String email = scanner.nextLine();

        User user = new User(id, name, email);
        try {
            library.addUser(user);
        } catch (DuplicateUserException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void removeUser() {
        System.out.println("Enter user ID:");
        int id = scanner.nextInt();
        try {
            library.removeUser(id);
        } catch(UserNotFoundException | UserHasBorrowedBooksException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void findUserById() {
        System.out.println("Enter user ID:");
        int id = scanner.nextInt();
        try {
            System.out.println(library.findUserById(id));
        } catch(UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
        scanner.nextLine();

        System.out.println("====================================");
    }

    public static void findUsersByName() {
        System.out.println("Enter user name:");
        String name = scanner.nextLine();
        try {
            System.out.println(library.findUsersByName(name));
        } catch(UserNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void findUsersByEmail() {
        System.out.println("Enter user email:");
        String email = scanner.nextLine();
        try {
            System.out.println(library.findUserByEmail(email));
        } catch(UserNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void borrowBook() {
        System.out.println("Enter user ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter book ISBN:");
        String isbn = scanner.nextLine();

        try {
            library.borrowBook(id, isbn);
        } catch(BookNotFoundException | UserNotFoundException | BookAlreadyBorrowedException | BorrowLimitExceededException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void returnBook() {
        System.out.println("Enter user ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter book ISBN:");
        String isbn = scanner.nextLine();

        try {
            library.returnBook(id, isbn);
        } catch(BookNotFoundException | UserNotFoundException | BookNotBorrowedException | BookNotFoundInUserException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void showAllBooks() {
        System.out.println("List of books:");
        try {
            List<Book> books = library.showAllBooks();
            for(Book book : books) {
                System.out.println(book);
            }
        } catch(LibraryIsEmptyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }

    public static void showAllUsers() {
        System.out.println("List of users:");
        try {
            List<User> users = library.showAllUsers();
            for(User user : users) {
                System.out.println(user);
            }
        } catch(NoUsersFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====================================");
    }
}
