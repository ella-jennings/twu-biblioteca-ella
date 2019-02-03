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
    private static final String MENU = "1 - List Of Books\nQ - Quit\n";
    private static final String BOOKINFO = "here is some book information";

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
        InOrder orderVerifier = inOrder(consolePrinter);
        orderVerifier.verify(consolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(consolePrinter).print(MENU);
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test
    public void ProcessUserInputCallsLibraryDisplayBooksIfOption1Selected() throws IOException {
        when(reader.readLine()).thenReturn("1");
        console.ProcessUserInput();
        verify(mockLibrary, times(1)).getBookInformation();
        verify(consolePrinter, times(1)).print(BOOKINFO);
        verify(consolePrinter, times(1)).printLine("Enter book title to check out:");
    }

    @Test
    public void ProcessUserInputCallsLibraryDisplayBooksIfInvalidOptionSelected() throws IOException {
        when(reader.readLine()).thenReturn("a");
        console.ProcessUserInput();
        when(reader.readLine()).thenReturn("2");
        console.ProcessUserInput();
        when(reader.readLine()).thenReturn("£");
        console.ProcessUserInput();
        when(reader.readLine()).thenReturn(" ");
        console.ProcessUserInput();

        verify(mockLibrary, times(0)).getBookInformation();
        verify(consolePrinter, times(4)).printLine("Please select a valid option!");
    }

    @Test
    public void UserInputQWillQuitApplication() throws IOException {
        when(reader.readLine()).thenReturn("Q");
        exit.expectSystemExitWithStatus(0);
        console.ProcessUserInput();
    }

    @Test
    public void UserCanCheckOutBook() throws IOException {
        when(reader.readLine()).thenReturn("1", "Dark Places");
        console.ProcessUserInput();
        verify(mockLibrary, times(1)).checkOut("Dark Places");
    }
}
