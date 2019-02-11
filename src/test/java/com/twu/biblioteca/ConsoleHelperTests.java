package com.twu.biblioteca;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ConsoleHelperTests {
    ConsoleHelper consoleHelper;
    private static final String MENU = "1 - List Of Books\n2 - Checkout a Book\n3 - Return a Book\n4 - List Of Movies\n5 - Checkout a Movie\n6 - Return a Movie\nL - Login\nQ - Quit\n";
    private static final String LOGGED_IN_USER = "1 - List Of Books\n2 - Checkout a Book\n3 - Return a Book\n4 - List Of Movies\n5 - Checkout a Movie\n6 - Return a Movie\nD - View my details\nL - Log out\nQ - Quit\n";

    @Mock
    User mockUser;

    @Before
    public void SetUp(){
        MockitoAnnotations.initMocks(this);
        consoleHelper = new ConsoleHelper();
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
}
