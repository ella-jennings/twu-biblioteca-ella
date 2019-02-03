package com.twu.biblioteca;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class LibraryTests {
    private String booksInformationAll = "Dark Places | Flynn, G | 2011\nTalent Is Overrated | Colvin, G | 2008\nFactfulness | Rosling, H | 2018\n";
    private Book book1 = new Book("Dark Places", "Gillian", "Flynn", "2011");
    private Book book2 = new Book("Talent Is Overrated", "Geoff", "Colvin", "2008");
    private Book book3 = new Book("Factfulness", "Hans", "Rosling", "2018");

    @Test
    public void DisplayBookInformationShouldReturnCorrectString() {
        Library library = new Library(Arrays.asList(book1, book2, book3));
        String result = library.getBookInformation();
        String expectedString = booksInformationAll;
        assertEquals(expectedString, result);
    }

    @Test
    public void CheckingOutBookShouldRemoveItFromBooksListAndReturnSuccess() {
        Library library = new Library(Arrays.asList(book1, book2, book3));
        String expectedSuccessMessage = library.checkOut("Talent Is Overrated");
        assertEquals("Thank you! Enjoy the book", expectedSuccessMessage);
        String result = library.getBookInformation();
        String expectedResult = "Dark Places | Flynn, G | 2011\nFactfulness | Rosling, H | 2018\n";
        assertEquals(expectedResult, result);
    }

    @Test
    public void InvalidBookNameShouldNotCHeckoutBookAndReturnFailure() {
        Library library = new Library(Arrays.asList(book1, book2, book3));
        String expectedFailureMessage = library.checkOut("Talant Is over-raated");
        assertEquals("Sorry, that book is not available", expectedFailureMessage);
        String result = library.getBookInformation();
        String expectedResult = booksInformationAll;
        assertEquals(expectedResult, result);
    }

    @Test
    public void ReturnBookShouldReturnBookAndReturnSuccess() {
        Library library = new Library(Arrays.asList(book1, book2, book3));
        library.checkOut("Talent Is Overrated");
        String expectedSuccessMessage = library.returnBook("Talent Is Overrated");
        assertEquals("Thank you for returning the book", expectedSuccessMessage);
        String expectedResult = booksInformationAll;
        assertEquals(expectedResult, expectedResult);
    }

}

