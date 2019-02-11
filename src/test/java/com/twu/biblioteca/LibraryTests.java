//package com.twu.biblioteca;
//
//import com.twu.biblioteca.LibraryItems.Movie;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Arrays;
//
//import static org.junit.Assert.assertEquals;
//
//public class LibraryTests {
//    private String booksInformationAll = "1 | Dark Places | Flynn, G | 2011\n2 | Talent Is Overrated | Colvin, G | 2008\n3 | Factfulness | Rosling, H | 2018\n";
//    private String movieInformationAll = "4 | Die Hard | 1990 | Director | Unrated\n5 | Die Hard 2 | 1992 | Director | 7/10\n";
//    private Book book1 = new Book(1,"Dark Places", "Gillian", "Flynn", 2011);
//    private Book book2 = new Book(2,"Talent Is Overrated", "Geoff", "Colvin", 2008);
//    private Book book3 = new Book(3,"Factfulness", "Hans", "Rosling", 2018);
//    private Movie movie1 = new Movie(4,"Die Hard", 1990, "Director");
//    private Movie movie2 = new Movie(5, "Die Hard 2", 1992, "Director", 7);
//    private User user = new User("1234-5678", "password");
//
//    private final String INVALID_TITLE = "Talant Is over-raated";
//    private Library library;
//
//    @Before
//    public void SetUp(){
//        library = new Library(Arrays.asList(book1, book2, book3, movie1,  movie2), );
//    }
//
//
//    @Test
//    public void DisplayBookInformationShouldReturnCorrectString() {
//        String result = library.getBookInformation();
//        String expectedString = booksInformationAll;
//        assertEquals(expectedString, result);
//    }
//
//    @Test
//    public void DisplayMovieInformationShouldReturnCorrectString() {
//        String result = library.getMovieInformation();
//        String expectedString = movieInformationAll;
//        assertEquals(expectedString, result);
//    }
//
//
//    // Check Out Functionality
//
//    // Book
//    @Test
//    public void CheckingOutBookWithTitleShouldRemoveItFromBooksListAndReturnSuccess() {
//        String expectedSuccessMessage = library.checkOutBook(book2.getTitle(), user);
//        assertEquals("Thank you! Enjoy the book", expectedSuccessMessage);
//        String result = library.getBookInformation();
//        String expectedResult = "1 | Dark Places | Flynn, G | 2011\n3 | Factfulness | Rosling, H | 2018\n";
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    public void CheckingOutBookWithIdShouldRemoveItFromBooksListAndReturnSuccess() {
//        String expectedSuccessMessage = library.checkOutBook(book2.getId().toString(), user);
//        assertEquals("Thank you! Enjoy the book", expectedSuccessMessage);
//        String result = library.getBookInformation();
//        String expectedResult = "1 | Dark Places | Flynn, G | 2011\n3 | Factfulness | Rosling, H | 2018\n";
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    public void InvalidBookNameShouldNotCheckoutBookAndReturnFailure() {
//        String expectedFailureMessage = library.checkOutBook(INVALID_TITLE, user);
//        assertEquals("Sorry, that book is not available", expectedFailureMessage);
//        String result = library.getBookInformation();
//        String expectedResult = booksInformationAll;
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    public void InvalidBookIdShouldNotCheckoutBookAndReturnFailure() {
//        String expectedFailureMessage = library.checkOutBook("5", user);
//        assertEquals("Sorry, that book is not available", expectedFailureMessage);
//        String result = library.getBookInformation();
//        String expectedResult = booksInformationAll;
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    public void ShouldNotCheckoutBookWhenAlreadyLoanedAndReturnFailure() {
//        library.checkOutBook(book2.getTitle(), user);
//        assertEquals(true, book2.isOnLoan());
//        String expectedFailureMessage = library.checkOutBook(book2.getTitle(), user);
//        assertEquals("Sorry, that book is not available", expectedFailureMessage);
//    }
//
//    // Movie
//
//    @Test
//    public void CheckingOutMovieWithTitleShouldRemoveItFromBooksListAndReturnSuccess() {
//        String expectedSuccessMessage = library.checkOutMovie(movie1.getTitle(), user);
//        assertEquals("Thank you! Enjoy the movie", expectedSuccessMessage);
//        String result = library.getMovieInformation();
//        String expectedResult = "5 | Die Hard 2 | 1992 | Director | 7/10\n";
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    public void CheckingOutMovieWithIdShouldRemoveItFromBooksListAndReturnSuccess() {
//        String expectedSuccessMessage = library.checkOutMovie(movie1.getId().toString(), user);
//        assertEquals("Thank you! Enjoy the movie", expectedSuccessMessage);
//        String result = library.getMovieInformation();
//        String expectedResult = "5 | Die Hard 2 | 1992 | Director | 7/10\n";
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    public void InvalidMovieNameShouldNotCheckoutBookAndReturnFailure() {
//        String expectedFailureMessage = library.checkOutMovie(INVALID_TITLE, user);
//        assertEquals("Sorry, that movie is not available", expectedFailureMessage);
//        String result = library.getBookInformation();
//        String expectedResult = booksInformationAll;
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    public void InvalidMovieIdShouldNotCheckoutBookAndReturnFailure() {
//        String expectedFailureMessage = library.checkOutMovie("2", user);
//        assertEquals("Sorry, that movie is not available", expectedFailureMessage);
//        String result = library.getBookInformation();
//        String expectedResult = booksInformationAll;
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    public void ShouldNotCheckoutMovieWhenAlreadyLoanedAndReturnFailure() {
//        library.checkOutMovie(movie1.getTitle(), user);
//        assertEquals(true, movie1.isOnLoan());
//        String expectedFailureMessage = library.checkOutMovie(movie1.getTitle(), user);
//        assertEquals("Sorry, that movie is not available", expectedFailureMessage);
//    }
//
//
//    // Return Functionality
//
//    // Book
//    @Test
//    public void ReturnBookShouldReturnBookAndReturnSuccess() {
//        library.checkOutBook(book2.getTitle(), user);
//        String expectedSuccessMessage = library.returnBook(book2.getTitle());
//        assertEquals("Thank you for returning the book", expectedSuccessMessage);
//        String expectedResult = booksInformationAll;
//        assertEquals(expectedResult, expectedResult);
//    }
//
//    @Test
//    public void InvalidBookOrNotInTheLibraryCannotBeReturned(){
//        library.checkOutBook(book2.getTitle(), user);
//        String expectedFailureMessage = library.returnBook(INVALID_TITLE);
//        assertEquals("That is not a valid book to return.", expectedFailureMessage);
//    }
//
//    @Test
//    public void BookNotOnLoanCannotBeReturned(){
//        assertEquals(false, book2.isOnLoan());
//        String expectedFailureMessage = library.returnBook(book2.getTitle());
//        assertEquals("That is not a valid book to return.", expectedFailureMessage);
//    }
//
//    // movie
//
//    @Test
//    public void ReturnMovieShouldReturnBookAndReturnSuccess() {
//        library.checkOutMovie(movie2.getTitle(), user);
//        String expectedSuccessMessage = library.returnMovie(movie2.getTitle());
//        assertEquals("Thank you for returning the movie", expectedSuccessMessage);
//        String expectedResult = movieInformationAll;
//        assertEquals(expectedResult, expectedResult);
//    }
//
//    @Test
//    public void InvalidMovieOrNotInTheLibraryCannotBeReturned(){
//        library.checkOutBook(movie2.getTitle(), user);
//        String expectedFailureMessage = library.returnMovie(INVALID_TITLE);
//        assertEquals("That is not a valid movie to return.", expectedFailureMessage);
//    }
//
//    @Test
//    public void MovieNotOnLoanCannotBeReturned(){
//        assertEquals(false, movie2.isOnLoan());
//        String expectedFailureMessage = library.returnMovie(movie2.getTitle());
//        assertEquals("That is not a valid movie to return.", expectedFailureMessage);
//    }
//
//}
//
