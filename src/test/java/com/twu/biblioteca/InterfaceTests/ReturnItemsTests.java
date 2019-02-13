package com.twu.biblioteca.InterfaceTests;

import com.twu.biblioteca.*;
import com.twu.biblioteca.LibraryItems.Movie;
import com.twu.biblioteca.MenuOptions.CheckOutItem;
import com.twu.biblioteca.MenuOptions.ReturnItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ReturnItemsTests {
    private InOrder orderVerifier;
    private String ITEM_NAME = "name";
    private String MESSAGE_TO_USER = "message";
    private ReturnItem returnBook;
    private ReturnItem returnMovie;
    private Class<Book> book = Book.class;
    private Class<Movie> movie = Movie.class;

    @Mock
    Library mockLibrary;
    @Mock
    ConsolePrinter mockConsolePrinter;
    @Mock
    ConsoleHelper mockConsoleHelper;
    @Mock
    UserValidator mockUserValidator;
    @Mock
    User mockUser;

    @Before
    public void Setup(){
        MockitoAnnotations.initMocks(this);
        orderVerifier = inOrder(mockConsolePrinter, mockLibrary, mockConsoleHelper, mockUserValidator);
        returnBook = new ReturnItem(mockLibrary, mockConsolePrinter, book, mockConsoleHelper, mockUserValidator);
        returnMovie = new ReturnItem(mockLibrary, mockConsolePrinter, Movie.class, mockConsoleHelper, mockUserValidator);


    }

    @Test
    public void UserReturnsIfNotLoggedInToCheckOutItem() {
        when(mockUserValidator.userIsLoggedIn()).thenReturn(false);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        returnBook.executeOption();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).logInUser();
        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        verify(mockUserValidator, times(0)).getCurrentUser();
    }

    @Test
    public void UserMustLogInToCheckOutItem() {
        when(mockUserValidator.userIsLoggedIn()).thenReturn(false, true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        returnBook.executeOption();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).logInUser();
        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
    }

    @Test
    public void UserCanCheckOutBook(){
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        when(mockConsoleHelper.getItemTitleFromUser("return", book)).thenReturn(ITEM_NAME);
        when(mockLibrary.returnItem(book, ITEM_NAME, mockUser)).thenReturn(MESSAGE_TO_USER);
        returnBook.executeOption();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
        orderVerifier.verify(mockConsoleHelper).getItemTitleFromUser("return", book);
        orderVerifier.verify(mockLibrary).returnItem(book, ITEM_NAME, mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(MESSAGE_TO_USER);
    }

    @Test
    public void UserCanCheckOutMovie(){
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        when(mockConsoleHelper.getItemTitleFromUser("return", movie)).thenReturn(ITEM_NAME);
        when(mockLibrary.returnItem(movie, ITEM_NAME, mockUser)).thenReturn(MESSAGE_TO_USER);
        returnMovie.executeOption();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
        orderVerifier.verify(mockConsoleHelper).getItemTitleFromUser("return", movie);
        orderVerifier.verify(mockLibrary).returnItem(movie, ITEM_NAME, mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(MESSAGE_TO_USER);
    }
}