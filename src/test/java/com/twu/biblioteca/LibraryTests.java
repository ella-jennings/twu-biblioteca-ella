package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class LibraryTests {
    private String booksInformationAll = "Dark Places | Flynn, G | 2011\nTalent Is Overrated | Colvin, G | 2008\nFactfulness | Rosling, H | 2018\n";
    private String movieInformationAll = "Die Hard | 1990 | Director | Unrated\nDie Hard 2 | 1992 | Director | 7/10\n";
    private Book book1 = new Book("Dark Places", "Gillian", "Flynn", 2011);
    private Book book2 = new Book("Talent Is Overrated", "Geoff", "Colvin", 2008);
    private Book book3 = new Book("Factfulness", "Hans", "Rosling", 2018);
    Movie movie1 = new Movie("Die Hard", 1990, "Director");
    Movie movie2 = new Movie("Die Hard 2", 1992, "Director", 7);

    private final String INVALID_TITLE = "Talant Is over-raated";
    private Library library;

    @Before
    public void SetUp(){
        library = new Library(Arrays.asList(book1, book2, book3), Arrays.asList(movie1, movie2));
    }


    @Test
    public void DisplayBookInformationShouldReturnCorrectString() {
        String result = library.getBookInformation();
        String expectedString = booksInformationAll;
        assertEquals(expectedString, result);
    }

    @Test
    public void DisplayMovieInformationShouldReturnCorrectString() {
        String result = library.getMovieInformation();
        String expectedString = movieInformationAll;
        assertEquals(expectedString, result);
    }

    @Test
    public void CheckingOutBookShouldRemoveItFromBooksListAndReturnSuccess() {
        String expectedSuccessMessage = library.checkOut(book2.getTitle());
        assertEquals("Thank you! Enjoy the book", expectedSuccessMessage);
        String result = library.getBookInformation();
        String expectedResult = "Dark Places | Flynn, G | 2011\nFactfulness | Rosling, H | 2018\n";
        assertEquals(expectedResult, result);
    }

    @Test
    public void InvalidBookNameShouldNotCheckoutBookAndReturnFailure() {
        String expectedFailureMessage = library.checkOut(INVALID_TITLE);
        assertEquals("Sorry, that book is not available", expectedFailureMessage);
        String result = library.getBookInformation();
        String expectedResult = booksInformationAll;
        assertEquals(expectedResult, result);
    }

    @Test
    public void ShouldNotCheckoutBookWhenAlreadyLoanedAndReturnFailure() {
        library.checkOut(book2.getTitle());
        assertEquals(true, book2.isOnLoan());
        String expectedFailureMessage = library.checkOut(book2.getTitle());
        assertEquals("Sorry, that book is not available", expectedFailureMessage);
    }

    @Test
    public void ReturnBookShouldReturnBookAndReturnSuccess() {
        library.checkOut(book2.getTitle());
        String expectedSuccessMessage = library.returnBook(book2.getTitle());
        assertEquals("Thank you for returning the book", expectedSuccessMessage);
        String expectedResult = booksInformationAll;
        assertEquals(expectedResult, expectedResult);
    }

    @Test
    public void InvalidBookOrNotInTheLibraryCannotBeReturned(){
        library.checkOut(book2.getTitle());
        String expectedFailureMessage = library.returnBook(INVALID_TITLE);
        assertEquals("That is not a valid book to return.", expectedFailureMessage);
    }

    @Test
    public void BookNotOnLoanCannotBeReturned(){
        assertEquals(false, book2.isOnLoan());
        String expectedFailureMessage = library.returnBook(book2.getTitle());
        assertEquals("That is not a valid book to return.", expectedFailureMessage);

    }

}

