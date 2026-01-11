package com.library.manager;

import com.library.exception.*;
import com.library.model.Book;
import com.library.model.User;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Book> books = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    public Library() {}

    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addBook(Book book) throws DuplicateBookException {
        for(Book Book : books) {
            if(Book.getIsbn().equals(book.getIsbn())) {
                throw new DuplicateBookException("Duplicate ISBN detected!");
            }
        }
        books.add(book);
        System.out.println("Book added successfully!");
    }

    public void removeBook(String isbn) throws BookBorrowedException, BookNotFoundException {
        Book bookToRemove = null;
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book.isBorrowed()) {
                    throw new BookBorrowedException("Book is currently borrowed!");
                } else {
                    bookToRemove = book;
                    break;
                }
            }
        }
        if (bookToRemove == null) {
            throw new BookNotFoundException("Book not found!");
        }
        books.remove(bookToRemove);
        System.out.println("Book deleted successfully!");
    }

    public Book findBookByIsbn(String isbn) throws BookNotFoundException {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        throw new BookNotFoundException("Book not found!");
    }

    public List<Book> findBooksByTitle(String title) throws BookNotFoundException, IllegalArgumentException {
        if(title == null || title.isBlank()) {
            throw new IllegalArgumentException("Search text cannot be empty!");
        }
        List<Book> foundBooks = new ArrayList<>();
        for(Book book : books) {
            if(book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        if(foundBooks.isEmpty()) {
            throw new BookNotFoundException("Book not found!");
        }
        return foundBooks;
    }

    public List<Book> findBooksByAuthor(String author) throws BookNotFoundException, IllegalArgumentException {
        if(author == null || author.isBlank()) {
            throw new IllegalArgumentException("Search text cannot be empty!");
        }
        List<Book> foundBooks = new ArrayList<>();
        for(Book book : books) {
            if(book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        if(foundBooks.isEmpty()) {
            throw new BookNotFoundException("Book not found!");
        }
        return foundBooks;
    }

    public List<Book> findBooksByPublishYear(int publishYear) throws BookNotFoundException {
        List<Book> foundBooks = new ArrayList<>();
        for(Book book : books) {
            if(book.getPublishYear() == publishYear) {
                foundBooks.add(book);
            }
        }
        if(foundBooks.isEmpty()) {
            throw new BookNotFoundException("Book not found!");
        }
        return foundBooks;
    }

    public List<Book> showAllBooks() throws LibraryIsEmptyException {
        if(books.isEmpty()) {
            throw new LibraryIsEmptyException("Library is empty!");
        }
        return books;
    }

    public void addUser(User user) throws DuplicateUserException {
        for(User User : users) {
            if(User.getId() == user.getId()) {
                throw new DuplicateUserException("Duplicate ID detected!");
            }
        }
        users.add(user);
        System.out.println("User added successfully!");
    }

    public void removeUser(int id) throws UserHasBorrowedBooksException, UserNotFoundException {
        User userToRemove = null;
        for(User user : users) {
            if(user.getId() == id) {
                if(!user.getBorrowedBooks().isEmpty()) {
                    throw new UserHasBorrowedBooksException("User has unreturned books!");
                } else {
                    userToRemove = user;
                    break;
                }
            }
        }
        if(userToRemove == null) {
            throw new UserNotFoundException("User not found!");
        }
        users.remove(userToRemove);
    }

    public User findUserById(int id) throws UserNotFoundException {
        for(User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }
        throw new UserNotFoundException("User not found!");
    }

    public List<User> findUsersByName(String name) throws UserNotFoundException, IllegalArgumentException {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Search text cannot be empty!");
        }
        List<User> foundUsers = new ArrayList<>();
        for(User user : users) {
            if(user.getName().toLowerCase().contains(name.toLowerCase())) {
                foundUsers.add(user);
            }
        }
        if(foundUsers.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        return foundUsers;
    }

    public List<User> findUserByEmail(String email) throws UserNotFoundException, IllegalArgumentException {
        if(email == null || email.isBlank()) {
            throw new IllegalArgumentException("Search text cannot be empty!");
        }
        List<User> foundUsers = new ArrayList<>();
        for(User user : users) {
            if(user.getEmail().toLowerCase().contains(email.toLowerCase())) {
                foundUsers.add(user);
            }
        }
        if(foundUsers.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        return foundUsers;
    }

    public List<User> showAllUsers() throws NoUsersFoundException {
        if(users.isEmpty()) {
            throw new NoUsersFoundException("No users found!");
        }
        return users;
    }

    public void borrowBook(int id, String isbn) throws BookNotFoundException, UserNotFoundException, BookAlreadyBorrowedException, BorrowLimitExceededException {
        Book book = findBookByIsbn(isbn);
        User user = findUserById(id);

        if(book.isBorrowed()) {
            throw new BookAlreadyBorrowedException("Book already borrowed!");
        }
        if(user.getBorrowedBooks().size() >= 3) {
            throw new BorrowLimitExceededException("User cannot borrow books more than 3!");
        }

        book.setBorrowed(true);
        user.getBorrowedBooks().add(isbn);
    }

    public void returnBook(int id, String isbn) throws BookNotFoundException, UserNotFoundException, BookNotBorrowedException, BookNotFoundInUserException {
        Book book = findBookByIsbn(isbn);
        User user = findUserById(id);

        if(!book.isBorrowed()) {
            throw new BookNotBorrowedException("Book is not borrowed!");
        }
        if(!user.getBorrowedBooks().contains(isbn)) {
            throw new BookNotFoundInUserException("Book not found in this user!");
        }

        book.setBorrowed(false);
        user.getBorrowedBooks().remove(isbn);
    }
}
