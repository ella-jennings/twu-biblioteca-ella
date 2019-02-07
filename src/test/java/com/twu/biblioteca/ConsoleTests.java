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
    private static final String MENU = "1 - List Of Books\n2 - Checkout a Book\n3 - Return a Book\n4 - List Of Movies\nQ - Quit\n";
    private static final String BOOKINFO = "here is some book information";
    private InOrder orderVerifier;

    @Mock
    Library mockLibrary;
    @Mock
    ConsolePrinter mockConsolePrinter;
    @Mock
    ConsoleReader mockConsoleReader;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void SetUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(mockLibrary.getBookInformation()).thenReturn(BOOKINFO);
        console = new Console(mockLibrary, mockConsolePrinter, mockConsoleReader);
        orderVerifier = inOrder(mockConsolePrinter, mockLibrary);
    }

    @Test
    public void InitialisingConsoleShouldPrintWelcomeMessage() {
        verify(mockConsolePrinter).printLine(WELCOME_MESSAGE);
    }

    @Test
    public void InitialisingConsoleShouldPrintMenuOptions() {

        verify(mockConsolePrinter).print(MENU);
    }

    @Test
    public void InitialisingConsolePrintsOptionsInCorrectOrder() {
        orderVerifier.verify(mockConsolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test
    public void ProcessUserInputCallsLibraryDisplayBooksIfOption1Selected() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("1");
        console.processUserInput();
        orderVerifier.verify(mockConsolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verify(mockLibrary).getBookInformation();
        orderVerifier.verify(mockConsolePrinter).print(MENU);
    }

    @Test
    public void ProcessUserInputCallsLibraryDisplayBooksIfInvalidOptionSelected() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("a");
        console.processUserInput();
        when(mockConsoleReader.getNextLine()).thenReturn("12");
        console.processUserInput();
        when(mockConsoleReader.getNextLine()).thenReturn("£");
        console.processUserInput();
        when(mockConsoleReader.getNextLine()).thenReturn(" ");
        console.processUserInput();

        orderVerifier.verify(mockConsolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
        orderVerifier.verify(mockConsolePrinter, times(4)).printLine("Please select a valid option!");

    }

    @Test
    public void UserInputQWillQuitApplication() throws IOException {
        when(mockConsoleReader.getNextLine()).thenReturn("Q");
        exit.expectSystemExitWithStatus(0);
        console.processUserInput();
    }

    @Test
    public void UserCanCheckOutBook() throws IOException {
        String bookName = "Dark Places";
        String messageToUser = "message";
        InOrder orderVerifier = inOrder(mockConsolePrinter, mockLibrary);

        when(mockConsoleReader.getNextLine()).thenReturn("2", bookName);
        when(mockLibrary.checkOut(bookName)).thenReturn(messageToUser);
        console.processUserInput();

        orderVerifier.verify(mockLibrary).checkOut(bookName);
        orderVerifier.verify(mockConsolePrinter).printLine(messageToUser);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
    }

    @Test public void UserCanReturnBook() throws IOException {
        String bookName = "Dark Places";
        String messageToUser = "message";
        when(mockConsoleReader.getNextLine()).thenReturn("3", bookName);
        when(mockLibrary.returnBook(bookName)).thenReturn(messageToUser);
        console.processUserInput();

        orderVerifier.verify(mockLibrary, times(1)).returnBook(bookName);
        orderVerifier.verify(mockConsolePrinter).printLine(messageToUser);
        orderVerifier.verify(mockConsolePrinter).print(MENU);
    }

    // Movie Tests

//    @Test
//    public void UserCanViewListOfMovies() throws IOException {
//        String movieInfo = "Here's some movie info";
//        when(reader.getNextLine()).thenReturn("4");
//        when(mockLibrary.getMovieInformation()).thenReturn(movieInfo);
//        console.processUserInput();
//        orderVerifier.verify(mockLibrary, times(1)).getMovieInformation();
//        orderVerifier.verify(consolePrinter, times(1)).print(movieInfo);
//        orderVerifier.verify(consolePrinter).print(MENU);
//
//    }
}
