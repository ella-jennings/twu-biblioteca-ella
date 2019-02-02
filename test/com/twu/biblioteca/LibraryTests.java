package com.twu.biblioteca;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LibraryTests {
    @Test
    public void GetBooksShouldReturnListOfBooks() {
        Library library = new Library();
        List<Book> result = library.getAllBooks();

        assertEquals(3, result.size());
        String bookInformation = new Book("Dark Places", "Gillian", "Flynn", "2011").getBookInformation();
        assertEquals(bookInformation, bookInformation);
    }

}
