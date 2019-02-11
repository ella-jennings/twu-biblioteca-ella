package com.twu.biblioteca;

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
    private static final String MENU = "1 - List Of Books\n2 - Checkout a Book\n3 - Return a Book\n4 - List Of Movies\n5 - Checkout a Movie\n6 - Return a Movie\nL - Login\nQ - Quit\n";
    private static final String LOGGED_IN_MENU = "1 - List Of Books\n2 - Checkout a Book\n3 - Return a Book\n4 - List Of Movies\n5 - Checkout a Movie\n6 - Return a Movie\nD - View my details\nL - Log out\nQ - Quit\n";
    private static final String USER_PROMPT_BOOK_CHECKOUT = "Enter book title or id to check out:";
    private static final String USER_PROMPT_BOOK_RETURN = "Enter book title or id to return:";
    private static final String USER_PROMPT_MOVIE_CHECKOUT = "Enter movie title or id to check out:";
    private static final String USER_PROMPT_MOVIE_RETURN = "Enter movie title or id to return:";
    private static final String BOOK_INFO = "here is some book information";
    private static final String QUIT_APPLICATION = "Q";
    private static String password = "password";
    private static String userId = "1234-5678";

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

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void SetUp() {
        MockitoAnnotations.initMocks(this);
        console = new Console(mockLibrary, mockConsolePrinter, mockConsoleReader, mockConsoleTerminator, mockUserValidator);
        orderVerifier = inOrder(mockConsolePrinter, mockLibrary, mockConsoleTerminator, mockConsoleReader, mockUserValidator);
    }


    @Test
    public void InitialisingConsolePrintsOptionsInCorrectOrder() {
        orderVerifier.verify(mockConsolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test
    public void ProcessUserInputCallsLibraryDisplayBooksIfOption1Selected() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("1", QUIT_APPLICATION);
        when(mockLibrary.getBookInformation()).thenReturn(BOOK_INFO);

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
    public void UserCanLogInAndSeesDifferentMenuTAndLogOut() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("L", "L", "L", QUIT_APPLICATION);
        when(mockUserValidator.logInUser()).thenReturn(mockUser);
        console.processUserInput();
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verify(mockUserValidator, times(1)).logInUser();
        orderVerifier.verify(mockConsolePrinter).print(LOGGED_IN_MENU);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verify(mockUserValidator).logInUser();
    }

    @Test
    public void UserOnlyHasToCheckInOnce() throws IOException {
        String bookName = "Dark Places";
        String messageToUser = "message";

        when(mockConsoleReader.getNextLine()).thenReturn("2", bookName, "2", bookName, QUIT_APPLICATION);
        when(mockLibrary.checkOutBook(eq(bookName), eq(mockUser))).thenReturn(messageToUser);
        when(mockUserValidator.logInUser()).thenReturn(mockUser);
        console.processUserInput();

        verify(mockUserValidator, times(1)).logInUser();
    }

    @Test
    public void LoggedInUserCanSeeTheirDetails() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("L", "D", QUIT_APPLICATION);
        when(mockUserValidator.logInUser()).thenReturn(mockUser);
        when(mockLibrary.getUserInformation(mockUser)).thenReturn("user info");
        console.processUserInput();

        orderVerifier.verify(mockLibrary).getUserInformation(mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine("user info");
    }

    @Test
    public void UserCanCheckOutBook() throws IOException {
        String bookName = "Dark Places";
        String messageToUser = "message";

        when(mockConsoleReader.getNextLine()).thenReturn("2", bookName, QUIT_APPLICATION);
        when(mockLibrary.checkOutBook(eq(bookName), eq(mockUser))).thenReturn(messageToUser);
        when(mockUserValidator.logInUser()).thenReturn(mockUser);
        console.processUserInput();

        orderVerifier.verify(mockUserValidator).logInUser();
        orderVerifier.verify(mockConsolePrinter).printLine(USER_PROMPT_BOOK_CHECKOUT);
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockLibrary).checkOutBook(bookName, mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(messageToUser);
        orderVerifier.verify(mockConsolePrinter).print(LOGGED_IN_MENU);
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockConsoleTerminator).exitApplication();
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test public void UserCanReturnBook() throws IOException {
        String bookName = "Dark Places";
        String messageToUser = "message";
        when(mockConsoleReader.getNextLine()).thenReturn("3", bookName, QUIT_APPLICATION);
        when(mockLibrary.returnBook(bookName, mockUser)).thenReturn(messageToUser);
        when(mockUserValidator.logInUser()).thenReturn(mockUser);

        console.processUserInput();

        orderVerifier.verify(mockUserValidator).logInUser();
        orderVerifier.verify(mockConsolePrinter).printLine(USER_PROMPT_BOOK_RETURN);
        orderVerifier.verify(mockLibrary, times(1)).returnBook(bookName, mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(messageToUser);
        orderVerifier.verify(mockConsolePrinter).print(LOGGED_IN_MENU);
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockConsoleTerminator).exitApplication();
        orderVerifier.verifyNoMoreInteractions();
    }

    // Movie Tests

    @Test
    public void UserCanViewListOfMovies() throws IOException {
        String movieInfo = "Here's some movie info";
        when(mockConsoleReader.getNextLine()).thenReturn("4", QUIT_APPLICATION);
        when(mockLibrary.getMovieInformation()).thenReturn(movieInfo);
        console.processUserInput();

        orderVerifier.verify(mockLibrary, times(1)).getMovieInformation();
        orderVerifier.verify(mockConsolePrinter, times(1)).printLine(movieInfo);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
    }

    @Test
    public void UserCanCheckOutMovie() throws IOException {
        String movieName = "movie title";
        String messageToUser = "message";

        when(mockConsoleReader.getNextLine()).thenReturn("5", movieName, QUIT_APPLICATION);
        when(mockLibrary.checkOutMovie(movieName, mockUser)).thenReturn(messageToUser);
        when(mockUserValidator.logInUser()).thenReturn(mockUser);

        console.processUserInput();

        orderVerifier.verify(mockUserValidator).logInUser();
        orderVerifier.verify(mockConsolePrinter).printLine(USER_PROMPT_MOVIE_CHECKOUT);
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockLibrary).checkOutMovie(movieName, mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(messageToUser);
        orderVerifier.verify(mockConsolePrinter).print(LOGGED_IN_MENU);
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockConsoleTerminator).exitApplication();
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test public void UserCanReturnMovie() throws IOException {
        String movieName = "movie title";
        String messageToUser = "message";
        when(mockConsoleReader.getNextLine()).thenReturn("6", movieName, QUIT_APPLICATION);
        when(mockLibrary.returnMovie(movieName, mockUser)).thenReturn(messageToUser);
        when(mockUserValidator.logInUser()).thenReturn(mockUser);

        console.processUserInput();

        orderVerifier.verify(mockUserValidator).logInUser();
        orderVerifier.verify(mockConsolePrinter).printLine(USER_PROMPT_MOVIE_RETURN);
        orderVerifier.verify(mockLibrary, times(1)).returnMovie(movieName, mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine(messageToUser);
        orderVerifier.verify(mockConsolePrinter).print(LOGGED_IN_MENU);
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockConsoleTerminator).exitApplication();
        orderVerifier.verifyNoMoreInteractions();
    }
}
