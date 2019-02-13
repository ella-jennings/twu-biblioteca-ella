package com.twu.biblioteca.InterfaceTests;

import com.twu.biblioteca.*;
import com.twu.biblioteca.LibraryItems.Movie;
import com.twu.biblioteca.MenuOptions.CheckOutItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class CheckOutItemTest {
    private InOrder orderVerifier;
    private String ITEM_NAME = "name";
    private String MESSAGE_TO_USER = "message";
    private CheckOutItem checkOutBook;
    private CheckOutItem checkOutMovie;
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
        checkOutBook = new CheckOutItem(mockLibrary, mockConsolePrinter, book, mockConsoleHelper, mockUserValidator);
        checkOutMovie = new CheckOutItem(mockLibrary, mockConsolePrinter, Movie.class, mockConsoleHelper, mockUserValidator);


    }

    @Test
    public void UserReturnsIfNotLoggedInToCheckOutItem() {
        when(mockUserValidator.userIsLoggedIn()).thenReturn(false);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        checkOutBook.executeOption();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).logInUser();
        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        verify(mockUserValidator, times(0)).getCurrentUser();
    }

    @Test
    public void UserMustLogInToCheckOutItem() {
        when(mockUserValidator.userIsLoggedIn()).thenReturn(false, true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        checkOutBook.executeOption();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).logInUser();
        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
    }

    @Test
    public void UserCanCheckOutBook(){
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        when(mockConsoleHelper.getItemTitleFromUser("check out", book)).thenReturn(ITEM_NAME);
        when(mockLibrary.checkOutItem(book, ITEM_NAME, mockUser)).thenReturn(MESSAGE_TO_USER);
        checkOutBook.executeOption();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
        orderVerifier.verify(mockConsoleHelper).getItemTitleFromUser("check out", book);
        orderVerifier.verify(mockLibrary).checkOutItem(book, ITEM_NAME, mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(MESSAGE_TO_USER);
    }

    @Test
    public void UserCanCheckOutMovie(){
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        when(mockConsoleHelper.getItemTitleFromUser("check out", movie)).thenReturn(ITEM_NAME);
        when(mockLibrary.checkOutItem(movie, ITEM_NAME, mockUser)).thenReturn(MESSAGE_TO_USER);
        checkOutMovie.executeOption();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
        orderVerifier.verify(mockConsoleHelper).getItemTitleFromUser("check out", movie);
        orderVerifier.verify(mockLibrary).checkOutItem(movie, ITEM_NAME, mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(MESSAGE_TO_USER);
    }
}
