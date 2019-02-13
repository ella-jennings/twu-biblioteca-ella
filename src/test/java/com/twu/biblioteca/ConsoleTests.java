package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;
import com.twu.biblioteca.MenuOptions.CheckOutItem;
import com.twu.biblioteca.MenuOptions.ListItems;
import com.twu.biblioteca.MenuOptions.ReturnItem;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(Theories.class)
public class ConsoleTests {
    private Console console;
    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!";
    private static final String MENU = "normal menu";
    private static final String LOGGED_IN_MENU = "Logged in menu";
    private static final String BOOK_INFO = "here is some book information";
    private static final String QUIT_APPLICATION = "Q";
    private static final String BOOK_NAME = "Dark Places";
    private static final String MOVIE_NAME = "Movie name";
    private static final String MESSAGE_TO_USER = "message";
    private static final String ERROR_MESSAGE = "Please select a valid option!";



    private InOrder orderVerifier;

    @Mock
    Library mockLibrary;
    @Mock
    ConsolePrinter mockConsolePrinter;
    @Mock
    ConsoleReader mockConsoleReader;
    @Mock
    ConsoleTerminator mockConsoleTerminator;
    @Mock
    User mockUser;
    @Mock
    UserValidator mockUserValidator;
    @Mock
    ConsoleHelper mockConsoleHelper;
    @Mock
    ListItems listBook;
    @Mock
    ListItems listMovie;
    @Mock
    CheckOutItem checkOutBook;
    @Mock
    CheckOutItem checkOutMovie;
    @Mock
    ReturnItem returnBook;
    @Mock
    ReturnItem returnMovie;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void SetUp() {
        MockitoAnnotations.initMocks(this);
        when(mockConsoleHelper.getMenu(false)).thenReturn(MENU);
        when(mockConsoleHelper.getMenu(true)).thenReturn(LOGGED_IN_MENU);
        console = new Console(mockLibrary, mockConsolePrinter, mockConsoleReader, mockConsoleTerminator, mockConsoleHelper, mockUserValidator, listBook, listMovie, checkOutBook, checkOutMovie, returnBook, returnMovie);
        orderVerifier = inOrder(mockConsolePrinter, mockLibrary, mockConsoleTerminator, mockConsoleReader, mockConsoleHelper, mockUserValidator, listBook, listMovie, checkOutBook, checkOutMovie, returnBook, returnMovie);


    }

    @Test
    public void InitialisingConsolePrintsOptionsInCorrectOrder() {
        orderVerifier.verify(mockConsolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(mockConsoleHelper).getMenu(false);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test
    public void ProcessUserInputCallsLibraryDisplayBooksIfInvalidOptionSelectedUntilValidSelected() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("a", "12", "D", "£", "", QUIT_APPLICATION);
        console.processUserInput();

        orderVerifier.verify(mockConsolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verify(mockConsolePrinter, times(5)).printLine(ERROR_MESSAGE);
    }

    @Test
    public void UserInputQWillQuitApplication() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn(QUIT_APPLICATION);
        console.processUserInput();
        verify(mockConsoleTerminator, times(1)).exitApplication();
    }

    @Test
    public void UserCanLogInAndSeesDifferentMenuAndLogOut() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("L", "L", "L", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(false,true, true, false, false, false);
        when(mockConsoleHelper.getMenu(true)).thenReturn(LOGGED_IN_MENU);
        when(mockConsoleHelper.getMenu(false)).thenReturn(MENU);
        console.processUserInput();
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verify(mockUserValidator, times(1)).logInUser();
        orderVerifier.verify(mockConsolePrinter).print(LOGGED_IN_MENU);
        orderVerifier.verify(mockUserValidator, times(1)).logOutUser();
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verify(mockUserValidator, times(1)).logInUser();
    }

    @Test
    public void LoggedInUserCanSeeTheirDetails() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("L", "D", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        when(mockLibrary.getUserInformation(mockUser)).thenReturn("user info");
        console.processUserInput();

        orderVerifier.verify(mockLibrary, times(1)).getUserInformation(mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine("user info");
    }

    @Test
    public void LoggedOutUserGetsErrorMessage() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn( "D", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(false);
        console.processUserInput();

        orderVerifier.verify(mockConsolePrinter).printLine(ERROR_MESSAGE);
    }

    // Book Tests

    @Test
    public void UserCanViewListOfBooks() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("1", QUIT_APPLICATION);
        console.processUserInput();

        orderVerifier.verify(listBook).executeOption();
        orderVerifier.verify(mockConsolePrinter).print(MENU);
    }

    @Test
    public void UserCanCheckOutBook() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("2", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        console.processUserInput();

        orderVerifier.verify(checkOutBook).executeOption();
        orderVerifier.verify(mockConsoleHelper).getMenu(true);
        orderVerifier.verify(mockConsoleTerminator).exitApplication();
    }

    @Test public void UserCanReturnBook() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("3", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        console.processUserInput();

        orderVerifier.verify(returnBook).executeOption();
        orderVerifier.verify(mockConsoleHelper).getMenu(true);
        orderVerifier.verify(mockConsoleTerminator).exitApplication();
    }

    // Movie Tests

    @Test
    public void UserCanViewListOfMovies() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("4", QUIT_APPLICATION);
        console.processUserInput();

        orderVerifier.verify(listMovie).executeOption();
        orderVerifier.verify(mockConsolePrinter).print(MENU);
    }


    @Test
    public void UserCanCheckOutMovie() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("5", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        console.processUserInput();

        orderVerifier.verify(checkOutMovie).executeOption();
        orderVerifier.verify(mockConsoleHelper).getMenu(true);
        orderVerifier.verify(mockConsoleTerminator).exitApplication();
    }

    @Test
    public void UserCanReturnMovie() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("6", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        console.processUserInput();

        orderVerifier.verify(returnMovie).executeOption();
        orderVerifier.verify(mockConsoleHelper).getMenu(true);
        orderVerifier.verify(mockConsoleTerminator).exitApplication();
    }
}
