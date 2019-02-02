package com.twu.biblioteca;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.mock;


public class ConsoleTests {
    private Console console;

    @Before
    public void SetUp() {
        Library mockLibrary = mock(Library.class);
        console = new Console(mockLibrary);
    }
    @Test
    public void GetWelcomeShouldReturnWelcomeMessage(){
        String result = console.getWelcomeMessage();

        Assert.assertEquals("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!", result);
    }
    @Test
    public void GetMenuOptionsShouldReturnAvailableOptions() {
        String result = console.getMenuOptions();

        Assert.assertEquals("1 - List Of Books", result);
    }

    @Test
    public void ProcessUserInputWithOption1CallsLibraryDisplayBooks(){
        console.ProcessUserInput(1);

//        Mockito.verify(mockLibrary).displayBookInformation();
    }
}
