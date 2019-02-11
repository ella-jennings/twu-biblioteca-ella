package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LibraryTests {

    private String booksInformationAll = "1 | Dark Places | Flynn, G | 2011\n2 | Talent Is Overrated | Colvin, G | 2008\n3 | Factfulness | Rosling, H | 2018\n";
    private String movieInformationAll = "4 | Die Hard | 1990 | Director | Unrated\n5 | Die Hard 2 | 1992 | Director | 7/10\n";


    private final String INVALID_TITLE = "Talant Is over-raated";
    private Library library;

    @Mock
    User user;
    @Mock
    User user2;
    @Mock
    User user3;

    @Mock
    Book book1;
    @Mock
    Book book2;
    @Mock
    Book book3;
    @Mock
    Movie movie1;
    @Mock
    Movie movie2;

    @Before
    public void SetUp(){
        MockitoAnnotations.initMocks(this);
        library = new Library(Arrays.asList(book1, book2, book3, movie1,  movie2), Arrays.asList(user3, user2));
        // user 3
        when(user3.getUserId()).thenReturn("1234-5678");
        when(user3.getName()).thenReturn("Mr Person");
        when(user3.getEmail()).thenReturn("email@gmail.com");
        when(user3.getPhone()).thenReturn("04562 845678 (ext 2)");
        when(user3.getCheckedOutItems()).thenReturn(Arrays.asList(book2, movie2));
        //book 1
        when(book1.getId()).thenReturn(1);
        when(book1.getTitle()).thenReturn("Dark Places");
        when(book1.getFirstName()).thenReturn("Gillian");
        when(book1.getLastName()).thenReturn("Flynn");
        when(book1.getDate()).thenReturn(2011);
        // book 2
        when(book2.getId()).thenReturn(2);
        when(book2.getTitle()).thenReturn("Talent Is Overrated");
        when(book2.getFirstName()).thenReturn("Geoff");
        when(book2.getLastName()).thenReturn("Colvin");
        when(book2.getDate()).thenReturn(2008);
        // book 3
        when(book3.getId()).thenReturn(3);
        when(book3.getTitle()).thenReturn("Factfulness");
        when(book3.getFirstName()).thenReturn("Hans");
        when(book3.getLastName()).thenReturn("Rosling");
        when(book3.getDate()).thenReturn(2018);
        // movie 1
        when(movie1.getId()).thenReturn(4);
        when(movie1.getTitle()).thenReturn("Die Hard");
        when(movie1.getDirector()).thenReturn("Director");
        when(movie1.getDate()).thenReturn(1990);
        when(movie1.getRating()).thenReturn("Unrated");
        // movie 2
        when(movie2.getId()).thenReturn(5);
        when(movie2.getTitle()).thenReturn("Die Hard 2");
        when(movie2.getDirector()).thenReturn("Director");
        when(movie2.getDate()).thenReturn(1992);
        when(movie2.getRating()).thenReturn("7/10");
    }

    @Test
    public void DisplayBookInformationShouldReturnCorrectString() {
        String result = library.getInformation(Book.class);
        String expectedString = booksInformationAll;
        assertEquals(expectedString, result);
    }

    @Test
    public void BookOnLoanShouldRemoveItFromDisplayInformation(){
        when(book2.isOnLoan()).thenReturn(true);
        String result = library.getInformation(Book.class);
        String expectedResult = "1 | Dark Places | Flynn, G | 2011\n3 | Factfulness | Rosling, H | 2018\n";
        assertEquals(expectedResult, result);
    }

    @Test
    public void DisplayMovieInformationShouldReturnCorrectString() {
        String result = library.getInformation(Movie.class);
        String expectedString = movieInformationAll;
        assertEquals(expectedString, result);
    }

    @Test
    public void MovieOnLoanShouldRemoveItFromDisplayInformation(){
        when(movie1.isOnLoan()).thenReturn(true);
        String result = library.getInformation(Movie.class);
        String expectedResult = "5 | Die Hard 2 | 1992 | Director | 7/10\n";
        assertEquals(expectedResult, result);
    }

    // User Details
    @Test
    public void GetUserDetailsShouldReturnDetailsAndItemsOnLoan(){
        String result = library.getUserInformation(user3);
        assertEquals("Details for user 1234-5678\nName: Mr Person\nEmail: email@gmail.com\nPhone: 04562 845678 (ext 2)\n\nItems on Loan:\n2 | Talent Is Overrated | Colvin, G | 2008\n5 | Die Hard 2 | 1992 | Director | 7/10\n", result);
    }

    @Test
    public void GetUserDetailsIncorrectUserShouldReturnError(){
        String result = library.getUserInformation(user);
        assertEquals("User is not a member of this library", result);

    }

    // Check Out Functionality

    // Book
    @Test
    public void CheckingOutBookWithTitleShouldRemoveItFromBooksListAndReturnSuccess() {
        when(book2.isOnLoan()).thenReturn(false);
        String expectedSuccessMessage = library.checkOutItem(Book.class, book2.getTitle(), user3);
        assertEquals("Thank you! Enjoy the book", expectedSuccessMessage);

        verify(user3, times(1)).addItem(book2);
    }

    @Test
    public void CheckingOutBookWithIdShouldRemoveItFromBooksListAndReturnSuccess() {
        when(book2.isOnLoan()).thenReturn(false);
        String expectedSuccessMessage = library.checkOutItem(Book.class, book2.getId().toString(), user3);
        assertEquals("Thank you! Enjoy the book", expectedSuccessMessage);
        verify(user3, times(1)).addItem(book2);
    }

    @Test
    public void InvalidBookNameShouldNotCheckoutBookAndReturnFailure() {
        when(book2.isOnLoan()).thenReturn(false);
        String expectedFailureMessage = library.checkOutItem(Book.class, INVALID_TITLE, user3);
        assertEquals("Sorry, that book is not available", expectedFailureMessage);
        verify(user3, times(0)).addItem(book2);
    }

    @Test
    public void InvalidBookIdShouldNotCheckoutBookAndReturnFailure() {
        when(book2.isOnLoan()).thenReturn(false);
        String expectedFailureMessage = library.checkOutItem(Book.class,"5", user3);
        assertEquals("Sorry, that book is not available", expectedFailureMessage);
        verify(user3, times(0)).addItem(book2);
    }

    @Test
    public void ShouldNotCheckoutBookWhenAlreadyLoanedAndReturnFailure() {
        when(book1.isOnLoan()).thenReturn(true);
        String expectedFailureMessage = library.checkOutItem(Book.class,book1.getTitle(), user3);
        assertEquals("Sorry, that book is not available", expectedFailureMessage);
        verify(user3, times(0)).addItem(book1);
    }


    // Movie

    @Test
    public void CheckingOutMovieWithTitleShouldRemoveItFromBooksListAndReturnSuccess() {
        when(movie1.isOnLoan()).thenReturn(false);
        String expectedSuccessMessage = library.checkOutItem(Movie.class, movie1.getTitle(), user3);
        assertEquals("Thank you! Enjoy the movie", expectedSuccessMessage);
        verify(user3, times(1)).addItem(movie1);
    }

    @Test
    public void CheckingOutMovieWithIdShouldRemoveItFromBooksListAndReturnSuccess() {
        when(movie1.isOnLoan()).thenReturn(false);
        String expectedSuccessMessage = library.checkOutItem(Movie.class, movie1.getId().toString(), user3);
        assertEquals("Thank you! Enjoy the movie", expectedSuccessMessage);
        verify(user3, times(1)).addItem(movie1);
    }

    @Test
    public void InvalidMovieNameShouldNotCheckoutBookAndReturnFailure() {
        String expectedFailureMessage = library.checkOutItem(Movie.class, INVALID_TITLE, user3);
        assertEquals("Sorry, that movie is not available", expectedFailureMessage);
    }

    @Test
    public void InvalidMovieIdShouldNotCheckoutBookAndReturnFailure() {
        String expectedFailureMessage = library.checkOutItem(Movie.class, "2", user3);
        assertEquals("Sorry, that movie is not available", expectedFailureMessage);
    }

    @Test
    public void ShouldNotCheckoutMovieWhenAlreadyLoanedAndReturnFailure() {
        when(movie1.isOnLoan()).thenReturn(true);
        String expectedFailureMessage = library.checkOutItem(Movie.class, movie1.getTitle(), user3);
        assertEquals("Sorry, that movie is not available", expectedFailureMessage);
        verify(user3, times(0)).addItem(movie1);
    }


    // Return Functionality

    // Book
    @Test
    public void ReturnBookShouldReturnBookAndReturnSuccess() {
        when(book2.isOnLoan()).thenReturn(true);
        String expectedSuccessMessage = library.returnItem(Book.class, book2.getTitle(), user3);
        assertEquals("Thank you for returning the book", expectedSuccessMessage);
        verify(user3, times(1)).removeItem(book2);
    }

    @Test
    public void InvalidBookOrNotInTheLibraryCannotBeReturned(){
        String expectedFailureMessage = library.returnItem(Book.class, INVALID_TITLE, user3);
        assertEquals("That is not a valid book to return.", expectedFailureMessage);
    }

    @Test
    public void BookNotOnLoanCannotBeReturned(){
        when(book1.isOnLoan()).thenReturn(false);
        String expectedFailureMessage = library.returnItem(Book.class, book1.getTitle(), user3);
        assertEquals("That is not a valid book to return.", expectedFailureMessage);
        verify(user3, times(0)).removeItem(book1);

    }

    @Test
    public void BookOnLoanByAnotherUserCannotBeReturned(){
        when(book2.isOnLoan()).thenReturn(true);
        String expectedFailureMessage = library.returnItem(Book.class, book2.getTitle(), user2);
        assertEquals("That is not a valid book to return.", expectedFailureMessage);
        verify(user3, times(0)).removeItem(book2);
        verify(user2, times(0)).removeItem(book2);
    }

    // movie

    @Test
    public void ReturnMovieShouldReturnBookAndReturnSuccess() {
        when(movie2.isOnLoan()).thenReturn(true);
        String expectedSuccessMessage = library.returnItem(Movie.class, movie2.getTitle(), user3);
        assertEquals("Thank you for returning the movie", expectedSuccessMessage);
        verify(user3, times(1)).removeItem(movie2);
    }

    @Test
    public void InvalidMovieOrNotInTheLibraryCannotBeReturned(){
        String expectedFailureMessage = library.returnItem(Movie.class, INVALID_TITLE, user3);
        assertEquals("That is not a valid movie to return.", expectedFailureMessage);
    }

    @Test
    public void MovieNotOnLoanCannotBeReturned(){
        when(movie2.isOnLoan()).thenReturn(false);
        String expectedFailureMessage = library.returnItem(Movie.class, movie2.getTitle(), user3);
        assertEquals("That is not a valid movie to return.", expectedFailureMessage);
        verify(user3, times(0)).removeItem(movie2);
    }

    @Test
    public void MovieOnLoanByAnotherUserCannotBeReturned(){
        when(movie2.isOnLoan()).thenReturn(true);
        String expectedFailureMessage = library.returnItem(Movie.class, movie2.getTitle(), user2);
        assertEquals("That is not a valid movie to return.", expectedFailureMessage);
        verify(user3, times(0)).removeItem(movie2);
        verify(user2, times(0)).removeItem(movie2);
    }

}

