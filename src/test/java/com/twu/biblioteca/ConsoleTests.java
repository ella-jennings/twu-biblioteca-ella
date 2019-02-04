package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
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
    ConsolePrinter consolePrinter;
    @Mock
    BufferedReader reader;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void SetUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(mockLibrary.getBookInformation()).thenReturn(BOOKINFO);
        console = new Console(mockLibrary, consolePrinter, reader);
        orderVerifier = inOrder(consolePrinter, mockLibrary);
    }

    @Test
    public void InitialisingConsoleShouldPrintWelcomeMessage() {
        verify(consolePrinter).printLine(WELCOME_MESSAGE);
    }

    @Test
    public void InitialisingConsoleShouldPrintMenuOptions() {

        verify(consolePrinter).print(MENU);
    }

    @Test
    public void InitialisingConsolePrintsOptionsInCorrectOrder() {
        orderVerifier.verify(consolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(consolePrinter).print(MENU);
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test
    public void ProcessUserInputCallsLibraryDisplayBooksIfOption1Selected() throws IOException {
        when(reader.readLine()).thenReturn("1");
        console.processUserInput();
        orderVerifier.verify(consolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(consolePrinter).print(MENU);
        orderVerifier.verify(mockLibrary).getBookInformation();
        orderVerifier.verify(consolePrinter).print(MENU);
    }

    @Test
    public void ProcessUserInputCallsLibraryDisplayBooksIfInvalidOptionSelected() throws IOException {
        when(reader.readLine()).thenReturn("a");
        console.processUserInput();
        when(reader.readLine()).thenReturn("12");
        console.processUserInput();
        when(reader.readLine()).thenReturn("£");
        console.processUserInput();
        when(reader.readLine()).thenReturn(" ");
        console.processUserInput();

        orderVerifier.verify(consolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(consolePrinter).print(MENU);
        orderVerifier.verify(consolePrinter, times(4)).printLine("Please select a valid option!");

    }

    @Test
    public void UserInputQWillQuitApplication() throws IOException {
        when(reader.readLine()).thenReturn("Q");
        exit.expectSystemExitWithStatus(0);
        console.processUserInput();
    }

    @Test
    public void UserCanCheckOutBook() throws IOException {
        String bookName = "Dark Places";
        String messageToUser = "message";
        InOrder orderVerifier = inOrder(consolePrinter, mockLibrary);

        when(reader.readLine()).thenReturn("2", bookName);
        when(mockLibrary.checkOut(bookName)).thenReturn(messageToUser);
        console.processUserInput();

        orderVerifier.verify(mockLibrary).checkOut(bookName);
        orderVerifier.verify(consolePrinter).printLine(messageToUser);
        orderVerifier.verify(consolePrinter).print(MENU);
    }

    @Test public void UserCanReturnBook() throws IOException {
        String bookName = "Dark Places";
        String messageToUser = "message";
        when(reader.readLine()).thenReturn("3", bookName);
        when(mockLibrary.returnBook(bookName)).thenReturn(messageToUser);
        console.processUserInput();

        orderVerifier.verify(mockLibrary, times(1)).returnBook(bookName);
        orderVerifier.verify(consolePrinter).printLine(messageToUser);
        orderVerifier.verify(consolePrinter).print(MENU);
    }

    // Movie Tests

//    @Test
//    public void UserCanViewListOfMovies() throws IOException {
//        String movieInfo = "Here's some movie info";
//        when(reader.readLine()).thenReturn("4");
//        when(mockLibrary.getMovieInformation()).thenReturn(movieInfo);
//        console.processUserInput();
//        orderVerifier.verify(mockLibrary, times(1)).getMovieInformation();
//        orderVerifier.verify(consolePrinter, times(1)).print(movieInfo);
//        orderVerifier.verify(consolePrinter).print(MENU);
//
//    }
}
