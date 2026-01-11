package com.library.manager;

import com.library.exception.*;
import com.library.model.Book;
import com.library.model.User;
import jdk.management.jfr.FlightRecorderMXBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    Library library = new Library();

    @Test
    public void addBookTest() throws DuplicateBookException {

        Book book1 = new Book("ISBN 1", "Title 1", "Author 1", 1985);
        library.addBook(book1);

        assertEquals(1, library.getBooks().size());
        assertEquals("Title 1", library.getBooks().getFirst().getTitle());

        Book book2 = new Book("ISBN 1", "Title 2", "Author 2", 1986);

        assertThrows(DuplicateBookException.class, () -> {
            library.addBook(book2);
        });
    }

    @Test
    public void removeBookTest() throws BookBorrowedException, BookNotFoundException {
        Book book1 = new Book("ISBN 1", "Title 1", "Author 1", 1985);
        Book book2 = new Book("ISBN 2", "Title 2", "Author 2", 1986);

        library.addBook(book1);
        library.addBook(book2);

        User user = new User(1,"User 1", "user1@gmail.com");
        library.addUser(user);

        library.borrowBook(1, "ISBN 2");
        library.removeBook("ISBN 1");

        assertEquals(1, library.getBooks().size());
        assertEquals("Title 2", library.getBooks().getFirst().getTitle());
        assertThrows(BookBorrowedException.class, () -> {
            library.removeBook("ISBN 2");
        });

        assertThrows(BookNotFoundException.class, () -> {
            library.removeBook("ISBN 3");
        });
    }

    @Test
    public void findBookByIsbnTest() throws BookNotFoundException {
        Book book1 = new Book("ISBN 1", "Title 1", "Author 1", 1985);
        Book book2 = new Book("ISBN 2", "Title 2", "Author 2", 1986);

        library.addBook(book1);

        assertEquals("Author 1", library.findBookByIsbn("ISBN 1").getAuthor());
        assertThrows(BookNotFoundException.class, () -> {
            library.findBookByIsbn("ISBN 2");
        });
    }

    @Test
    public void findBooksByTitleTest() throws BookNotFoundException, IllegalArgumentException {
        Book book1 = new Book("ISBN 1", "Title 1", "Author 1", 1985);
        Book book2 = new Book("ISBN 2", "Title 1", "Author 2", 1986);

        library.addBook(book1);
        library.addBook(book2);

        assertEquals(1985, library.findBooksByTitle("Title 1").getFirst().getPublishYear());
        assertEquals(2, library.findBooksByTitle("Title 1").size());
        assertThrows(IllegalArgumentException.class, () -> {
            library.findBooksByTitle(" ");
        });
        assertThrows(BookNotFoundException.class, () -> {
            library.findBooksByTitle("Title 3");
        });
    }

    @Test
    public void findBooksByAuthorTest() throws BookNotFoundException, IllegalArgumentException {
        Book book1 = new Book("ISBN 1", "Title 1", "Author 1", 1985);
        Book book2 = new Book("ISBN 2", "Title 2", "Author 2", 1986);

        library.addBook(book1);
        library.addBook(book2);

        assertEquals(1985, library.findBooksByAuthor("Author 1").getFirst().getPublishYear());
        assertEquals(1, library.findBooksByAuthor("Author 1").size());
        assertThrows(IllegalArgumentException.class, () -> {
            library.findBooksByTitle(null);
        });
        assertThrows(BookNotFoundException.class, () -> {
            library.findBooksByAuthor("Author 3");
        });
    }

    @Test
    public void findBooksByPublishYearTest() throws BookNotFoundException {
        Book book1 = new Book("ISBN 1", "Title 1", "Author 1", 1985);
        Book book2 = new Book("ISBN 2", "Title 2", "Author 2", 1986);
        Book book3 = new Book("ISBN 3", "Title 3", "Author 3", 1986);

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        assertEquals("Author 1", library.findBooksByPublishYear(1985).getFirst().getAuthor());
        assertEquals(2, library.findBooksByPublishYear(1986).size());
        assertThrows(BookNotFoundException.class, () -> {
            library.findBooksByPublishYear(1987);
        });
    }

    @Test
    public void showAllBooksTest() throws LibraryIsEmptyException {
        Book book1 = new Book("ISBN 1", "Title 1", "Author 1", 1985);
        Book book2 = new Book("ISBN 2", "Title 2", "Author 2", 1986);

        library.addBook(book1);
        library.addBook(book2);

        assertEquals(2, library.showAllBooks().size());

        library.removeBook("ISBN 1");
        library.removeBook("ISBN 2");

        assertThrows(LibraryIsEmptyException.class, () -> {
            library.showAllBooks();
        });
    }

    @Test
    public void addUserTest() throws DuplicateUserException {
        User user1 = new User(1, "User 1", "user1@gmail.com");
        library.addUser(user1);

        assertEquals(1, library.getUsers().size());
        assertEquals(1, library.getUsers().getFirst().getId());

        User user2 = new User(1, "User 2", "user2@gmail.com");

        assertThrows(DuplicateUserException.class, () -> {
            library.addUser(user2);
        });
    }

    @Test
    public void removeUserTest() throws UserHasBorrowedBooksException, UserNotFoundException {
        User user1 = new User(1,"User 1", "user1@gmail.com");
        User user2 = new User(2,"User 2", "user2@gmail.com");

        library.addUser(user1);
        library.addUser(user2);

        Book book = new Book("ISBN 1", "Title 1", "Author 1", 1985);

        library.addBook(book);

        library.removeUser(1);

        assertEquals(1, library.getUsers().size());

        library.borrowBook(2, "ISBN 1");

        assertThrows(UserHasBorrowedBooksException.class, () -> {
            library.removeUser(2);
        });
        assertThrows(UserNotFoundException.class, () -> {
            library.removeUser(3);
        });
    }

    @Test
    public void findUserByIdTest() throws UserNotFoundException {
        User user1 = new User(1,"User 1", "user1@gmail.com");
        User user2 = new User(2,"User 2", "user2@gmail.com");

        library.addUser(user1);

        assertEquals("User 1", library.findUserById(1).getName());
        assertThrows(UserNotFoundException.class, () -> {
            library.findUserById(2);
        });
    }

    @Test
    public void findUsersByNameTest() throws UserNotFoundException, IllegalArgumentException {
        User user1 = new User(1,"User 1", "user1@gmail.com");
        User user2 = new User(2,"User 2", "user2@gmail.com");

        library.addUser(user1);
        library.addUser(user2);

        assertEquals(1, library.findUsersByName("User 1").getFirst().getId());
        assertEquals(1, library.findUsersByName("User 1").size());
        assertThrows(IllegalArgumentException.class, () -> {
            library.findUsersByName("");
        });
        assertThrows(UserNotFoundException.class, () -> {
            library.findUsersByName("User 3");
        });

    }

    @Test
    public void findUserByEmailTest() throws UserNotFoundException, IllegalArgumentException {
        User user1 = new User(1,"User 1", "user1@gmail.com");
        User user2 = new User(2,"User 2", "user2@gmail.com");

        library.addUser(user1);
        library.addUser(user2);

        assertEquals(1, library.findUserByEmail("user1@gmail.com").getFirst().getId());
        assertEquals(1, library.findUserByEmail("user1@gmail.com").size());
        assertThrows(IllegalArgumentException.class, () -> {
            library.findUserByEmail(null);
        });
        assertThrows(UserNotFoundException.class, () -> {
            library.findUserByEmail("user3@gmail.com");
        });
    }

    @Test
    public void showAllUsersTest() throws NoUsersFoundException {
        User user1 = new User(1,"User 1", "user1@gmail.com");
        User user2 = new User(2,"User 2", "user2@gmail.com");

        library.addUser(user1);
        library.addUser(user2);

        assertEquals(2, library.showAllUsers().size());

        library.removeUser(1);
        library.removeUser(2);

        assertThrows(NoUsersFoundException.class, () -> {
            library.showAllUsers();
        });
    }

    @Test
    public void borrowBookTest() throws BookNotFoundException, UserNotFoundException, BookAlreadyBorrowedException, BorrowLimitExceededException {
        Book book1 = new Book("ISBN 1", "Title 1", "Author 1", 1985);
        Book book2 = new Book("ISBN 2", "Title 2", "Author 2", 1986);
        Book book3 = new Book("ISBN 3", "Title 3", "Author 3", 1987);
        Book book4 = new Book("ISBN 4", "Title 4", "Author 4", 1988);

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);

        User user1 = new User(1,"User 1", "user1@gmail.com");
        User user2 = new User(2,"User 2", "user2@gmail.com");

        library.addUser(user1);
        library.addUser(user2);

        library.borrowBook(1, "ISBN 1");

        Book borrowedBook = library.findBookByIsbn(
                user1.getBorrowedBooks().getFirst()
        );

        assertEquals(1985, borrowedBook.getPublishYear());

        library.borrowBook(2, "ISBN 2");
        library.borrowBook(2, "ISBN 3");
        library.borrowBook(2, "ISBN 4");

        Book book5 = new Book("ISBN 5", "Title 5", "Author 5", 1989);
        library.addBook(book5);

        assertThrows(BookAlreadyBorrowedException.class, () -> {
            library.borrowBook(2, "ISBN 1");
        });
        assertThrows(BorrowLimitExceededException.class, () -> {
            library.borrowBook(2, "ISBN 5");
        });
    }

    @Test
    public void returnBookTest() throws BookNotFoundException, UserNotFoundException, BookNotBorrowedException, BookNotFoundInUserException {
        Book book = new Book("ISBN 1", "Title 1", "Author 1", 1985);

        library.addBook(book);

        User user1 = new User(1,"User 1", "user1@gmail.com");
        User user2 = new User(2,"User 2", "user2@gmail.com");

        library.addUser(user1);
        library.addUser(user2);

        library.borrowBook(1, "ISBN 1");
        library.returnBook(1, "ISBN 1");

        assertEquals(0, user1.getBorrowedBooks().size());
        assertFalse(book.isBorrowed());
        assertThrows(BookNotBorrowedException.class, () -> {
            library.returnBook(1, "ISBN 1");
        });

        library.borrowBook(1, "ISBN 1");

        assertThrows(BookNotFoundInUserException.class, () -> {
            library.returnBook(2, "ISBN 1");
        });
    }

}