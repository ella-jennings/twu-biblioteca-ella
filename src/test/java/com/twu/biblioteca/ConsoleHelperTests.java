package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class ConsoleHelperTests {
    ConsoleHelper consoleHelper;
    private static final String MENU = "1 - List Of Books\n2 - Checkout a Book\n3 - Return a Book\n4 - List Of Movies\n5 - Checkout a Movie\n6 - Return a Movie\nL - Login\nQ - Quit\n";
    private static final String LOGGED_IN_USER = "1 - List Of Books\n2 - Checkout a Book\n3 - Return a Book\n4 - List Of Movies\n5 - Checkout a Movie\n6 - Return a Movie\nD - View my details\nL - Log out\nQ - Quit\n";
    private static final String USER_PROMPT_BOOK_CHECKOUT = "Enter book title or id to check out:";
    private static final String USER_PROMPT_BOOK_RETURN = "Enter book title or id to return:";
    private static final String USER_PROMPT_MOVIE_CHECKOUT = "Enter movie title or id to check out:";
    private static final String USER_PROMPT_MOVIE_RETURN = "Enter movie title or id to return:";

    @Mock
    User mockUser;

    @Mock
    ConsolePrinter consolePrinter;

    @Mock
    ConsoleReader consoleReader;

    @Before
    public void SetUp(){
        MockitoAnnotations.initMocks(this);
        consoleHelper = new ConsoleHelper(consolePrinter,consoleReader);
    }

    @Test
    public void CallingGetMenuWithNullUserReturnsBasicMenu(){
        String menu = consoleHelper.getMenu(null);
        Assert.assertEquals(MENU, menu);
    }

    @Test
    public void CallingGetMenuWithLoggedInUserReturnsBasicMenu(){
        String menu = consoleHelper.getMenu(mockUser);
        Assert.assertEquals(LOGGED_IN_USER, menu);
    }

    @Test
    public void CallingGetItemTitleFormatsCorrectlyCheckoutBook(){
        consoleHelper.getItemTitleFromUser("check out", Book.class);
        verify(consolePrinter).printLine(USER_PROMPT_BOOK_CHECKOUT);
        verify(consoleReader).getNextLine();
    }

    @Test
    public void CallingGetItemTitleFormatsCorrectlyReturnBook(){
        consoleHelper.getItemTitleFromUser("return", Book.class);
        verify(consolePrinter).printLine(USER_PROMPT_BOOK_RETURN);
        verify(consoleReader).getNextLine();
    }

    @Test
    public void CallingGetItemTitleFormatsCorrectlyCheckoutMovie(){
        consoleHelper.getItemTitleFromUser("check out", Movie.class);
        verify(consolePrinter).printLine(USER_PROMPT_MOVIE_CHECKOUT);
        verify(consoleReader).getNextLine();
    }

    @Test
    public void CallingGetItemTitleFormatsCorrectlyReturnMovie(){
        consoleHelper.getItemTitleFromUser("return", Movie.class);
        verify(consolePrinter).printLine(USER_PROMPT_MOVIE_RETURN);
        verify(consoleReader).getNextLine();
    }
}
