package com.example.techtasknerdysoft.utils;

import com.example.techtasknerdysoft.exception.BookNotAvailableException;
import com.example.techtasknerdysoft.exception.UserHasMaximumBooksException;
import com.example.techtasknerdysoft.exception.UserNotFoundBookException;
import com.example.techtasknerdysoft.model.Book;
import com.example.techtasknerdysoft.model.User;

public class BookUtils {
    public static void checkBookAvailability(Book book) {
        if (book.getAmount() <= 0) {
            throw new BookNotAvailableException("This book is not available");
        }
    }

    public static void checkUserBooks(User user) {
        if (user.getBooks().size() >= 10) {
            throw new UserHasMaximumBooksException("User already has the "
                    + "maximum number of borrowed books");
        }
    }

    public static void isBookBorrowedByUser(User user, Book book) {
        if (!user.getBooks().contains(book)) {
            throw new UserNotFoundBookException("User does not have this book");
        }
    }
}
