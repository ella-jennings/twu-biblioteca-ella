package com.twu.biblioteca;

import com.twu.biblioteca.MenuOptions.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.experimental.theories.Theories;
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
    private static final String QUIT_APPLICATION = "Q";
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
    @Mock
    GetDetails getDetails;
    @Mock
    Login login;
    @Mock
    Quit quit;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void SetUp() {
        MockitoAnnotations.initMocks(this);
        when(mockConsoleHelper.getMenu(false)).thenReturn(MENU);
        when(mockConsoleHelper.getMenu(true)).thenReturn(LOGGED_IN_MENU);
        console = new Console(mockConsolePrinter, mockConsoleReader, mockConsoleHelper, mockUserValidator, listBook, listMovie, checkOutBook, checkOutMovie, returnBook, returnMovie, getDetails, login, quit);
        orderVerifier = inOrder(mockConsolePrinter, mockLibrary, mockConsoleTerminator, mockConsoleReader, mockConsoleHelper, mockUserValidator, listBook, listMovie, checkOutBook, checkOutMovie, returnBook, returnMovie, getDetails, login, quit);


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
        when(mockConsoleReader.getNextLine()).thenReturn("a", "12", "\n", "£", "", QUIT_APPLICATION);
        console.processUserInput();

        orderVerifier.verify(mockConsolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verify(mockConsolePrinter, times(5)).printLine(ERROR_MESSAGE);
    }

    @Test
    public void UserInputQWillQuitApplication() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn(QUIT_APPLICATION);
        console.processUserInput();
        orderVerifier.verify(quit).executeOption();
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test
    public void UserInputDWillGetDetails() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("D", QUIT_APPLICATION);
        console.processUserInput();
        orderVerifier.verify(getDetails).executeOption();
        orderVerifier.verify(quit).executeOption();
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test
    public void UserCanLogInAndLogOut() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("L", QUIT_APPLICATION);
        console.processUserInput();
        orderVerifier.verify(login).executeOption();
        orderVerifier.verify(quit).executeOption();
        orderVerifier.verifyNoMoreInteractions();
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
        orderVerifier.verify(quit).executeOption();
    }

    @Test public void UserCanReturnBook() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("3", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        console.processUserInput();

        orderVerifier.verify(returnBook).executeOption();
        orderVerifier.verify(mockConsoleHelper).getMenu(true);
        orderVerifier.verify(quit).executeOption();

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
        orderVerifier.verify(quit).executeOption();

    }

    @Test
    public void UserCanReturnMovie() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("6", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        console.processUserInput();

        orderVerifier.verify(returnMovie).executeOption();
        orderVerifier.verify(mockConsoleHelper).getMenu(true);
        orderVerifier.verify(quit).executeOption();

    }
}
