package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;


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

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void SetUp() {
        MockitoAnnotations.initMocks(this);
        when(mockConsoleHelper.getMenu(false)).thenReturn(MENU);
        when(mockConsoleHelper.getMenu(true)).thenReturn(LOGGED_IN_MENU);
        console = new Console(mockLibrary, mockConsolePrinter, mockConsoleReader, mockConsoleTerminator, mockConsoleHelper, mockUserValidator);
        orderVerifier = inOrder(mockConsolePrinter, mockLibrary, mockConsoleTerminator, mockConsoleReader, mockConsoleHelper, mockUserValidator);

    }

    @Test
    public void InitialisingConsolePrintsOptionsInCorrectOrder() {
        orderVerifier.verify(mockConsolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(mockConsoleHelper).getMenu(false);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test
    public void ProcessUserInputCallsLibraryDisplayBooksIfOption1Selected() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("1", QUIT_APPLICATION);
        when(mockLibrary.getInformation(Book.class)).thenReturn(BOOK_INFO);

        console.processUserInput();
        orderVerifier.verify(mockConsolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockConsolePrinter).printLine(BOOK_INFO);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockConsoleTerminator).exitApplication();
    }

    @Test
    public void ProcessUserInputCallsLibraryDisplayBooksIfInvalidOptionSelectedUntilValidSelected() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("a", "12", "D", "£", "", QUIT_APPLICATION);
        console.processUserInput();

        orderVerifier.verify(mockConsolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verify(mockConsolePrinter, times(5)).printLine("Please select a valid option!");
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
//
//    @Test
//    public void LoggedInUserCanSeeTheirDetails() throws IOException {
//        when(mockConsoleReader.getNextLine()).thenReturn("L", "D", QUIT_APPLICATION);
//        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
//        when(mockLibrary.getUserInformation(mockUser)).thenReturn("user info");
//        console.processUserInput();
//
//        orderVerifier.verify(mockLibrary, times(1)).getUserInformation(mockUser);
//        orderVerifier.verify(mockConsolePrinter).printLine("user info");
//    }

    @Test
    public void UserCanCheckOutBook() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("2", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        when(mockConsoleHelper.getItemTitleFromUser("check out", Book.class)).thenReturn(BOOK_NAME);
        when(mockLibrary.checkOutItem(Book.class, BOOK_NAME, mockUser)).thenReturn(MESSAGE_TO_USER);
        console.processUserInput();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
        orderVerifier.verify(mockConsoleHelper).getItemTitleFromUser("check out", Book.class);
        orderVerifier.verify(mockLibrary).checkOutItem(Book.class, BOOK_NAME, mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(MESSAGE_TO_USER);
        orderVerifier.verify(mockConsoleHelper).getMenu(true);
        orderVerifier.verify(mockConsoleTerminator).exitApplication();
    }

    @Test public void UserCanReturnBook() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("3", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        when(mockConsoleHelper.getItemTitleFromUser("return", Book.class)).thenReturn(BOOK_NAME);
        when(mockLibrary.returnItem(Book.class, BOOK_NAME, mockUser)).thenReturn(MESSAGE_TO_USER);

        console.processUserInput();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
        orderVerifier.verify(mockConsoleHelper).getItemTitleFromUser("return", Book.class);
        orderVerifier.verify(mockLibrary).returnItem(Book.class, BOOK_NAME, mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(MESSAGE_TO_USER);
        orderVerifier.verify(mockConsoleHelper).getMenu(true);
    }

    // Movie Tests

    @Test
    public void UserCanViewListOfMovies() throws IOException {
        String movieInfo = "Here's some movie info";
        when(mockConsoleReader.getNextLine()).thenReturn("4", QUIT_APPLICATION);
        when(mockLibrary.getInformation(Movie.class)).thenReturn(movieInfo);
        console.processUserInput();

        orderVerifier.verify(mockLibrary, times(1)).getInformation(Movie.class);
        orderVerifier.verify(mockConsolePrinter, times(1)).printLine(movieInfo);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
    }

    @Test
    public void UserMustLogInToCheckOutMovie() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("5", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(false, true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        console.processUserInput();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).logInUser();
        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
    }


    @Test
    public void UserMustLogInToReturnMovie() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("6", QUIT_APPLICATION);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(false, true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        console.processUserInput();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).logInUser();
        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
    }

    @Test
    public void UserCanCheckOutMovie() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("5", QUIT_APPLICATION);
        when(mockConsoleHelper.getItemTitleFromUser("check out", Movie.class)).thenReturn(MOVIE_NAME);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        when(mockLibrary.checkOutItem(Movie.class, MOVIE_NAME, mockUser)).thenReturn(MESSAGE_TO_USER);
        console.processUserInput();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
        orderVerifier.verify(mockConsoleHelper).getItemTitleFromUser("check out", Movie.class);
        orderVerifier.verify(mockLibrary).checkOutItem(Movie.class, MOVIE_NAME, mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(MESSAGE_TO_USER);
        orderVerifier.verify(mockConsoleHelper).getMenu(true);
        orderVerifier.verify(mockConsoleTerminator).exitApplication();
    }

    @Test public void UserCanReturnMovie() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("6", QUIT_APPLICATION);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        when(mockConsoleHelper.getItemTitleFromUser("return", Movie.class)).thenReturn(MOVIE_NAME);
        when(mockLibrary.returnItem(Movie.class, MOVIE_NAME, mockUser)).thenReturn(MESSAGE_TO_USER);

        console.processUserInput();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).getCurrentUser();
        orderVerifier.verify(mockConsoleHelper).getItemTitleFromUser("return", Movie.class);
        orderVerifier.verify(mockLibrary).returnItem(Movie.class, MOVIE_NAME , mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(MESSAGE_TO_USER);
        orderVerifier.verify(mockConsoleHelper).getMenu(true);
    }
}
