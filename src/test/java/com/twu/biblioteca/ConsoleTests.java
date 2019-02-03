package com.twu.biblioteca;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ConsoleTests {
    private Console console;
    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!";
    private static final String MENU = "1 - List Of Books";

    @Mock
    Library mockLibrary;
    @Mock
    ConsolePrinter consolePrinter;

    @Before
    public void SetUp() {
        MockitoAnnotations.initMocks(this);
        console = new Console(mockLibrary, consolePrinter);

    }
    @Test
    public void InitialisingConsoleShouldPrintWelcomeMessage(){
        verify(consolePrinter).printLine(WELCOME_MESSAGE);
    }
    @Test
    public void InitialisingConsoleShouldPrintMenuOptions() {

        verify(consolePrinter).printLine(MENU);
    }
    @Test
    public void InitialisingConsolePrintsOptionsInCorrectOrder(){
        InOrder orderVerifier = Mockito.inOrder(consolePrinter);
        orderVerifier.verify(consolePrinter).printLine(WELCOME_MESSAGE);
        orderVerifier.verify(consolePrinter).printLine(MENU);
        orderVerifier.verifyNoMoreInteractions();
    }

//    @Test
//    public void ProcessUserInputWith1CallsLibraryDisplayBooks(){
//        when(userInput.nextLine()).thenReturn("1");
//
//        Mockito.verify(mockLibrary).displayBookInformation();
//    }
}
