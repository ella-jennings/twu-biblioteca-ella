package com.twu.biblioteca;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class UserValidatorTests {
    private String CORRECT_USER_ID = "1234-5678";
    private String CORRECT_PASSWORD = "password";
    private UserValidator userValidator;
    private InOrder orderVerifier;

    @Mock
    User mockUser;

    @Mock
    ConsoleReader mockConsoleReader;

    @Mock
    ConsolePrinter mockConsolePrinter;

    @Mock
    Library mockLibrary;

    @Before
    public void Setup(){
        MockitoAnnotations.initMocks(this);
        orderVerifier = inOrder(mockConsolePrinter, mockConsoleReader, mockLibrary);
        userValidator = new UserValidator(mockConsoleReader, mockConsolePrinter, mockLibrary);
        when(mockLibrary.getUsers()).thenReturn(Arrays.asList(mockUser, new User("1235-8906", "hello")));
        when(mockUser.getUserId()).thenReturn(CORRECT_USER_ID);
        when(mockUser.isCorrectPassword(CORRECT_PASSWORD)).thenReturn(true);
    }

    @Test
    public void CallingLogInUserAndProvidingCorrectDetailsWillReturnUserProfile() {
        when(mockConsoleReader.getNextLine()).thenReturn(CORRECT_USER_ID, CORRECT_PASSWORD);
        Assert.assertEquals(mockUser, userValidator.logInUser());
    }

    @Test
    public void CallingLogInUserWithIncorrectDetailsWillPromptForReAttempt(){
        when(mockConsoleReader.getNextLine()).thenReturn("45-23", "9999-9999", CORRECT_USER_ID, CORRECT_PASSWORD);
        User result = userValidator.logInUser();
        orderVerifier.verify(mockConsolePrinter).printLine("Please enter User Id: ");
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockLibrary).getUsers();
        orderVerifier.verify(mockConsolePrinter).printLine("User not found! User Id must be in format XXXX-XXXX where X is a number");
        orderVerifier.verify(mockConsolePrinter).printLine("Please enter User Id: ");
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockLibrary).getUsers();
        orderVerifier.verify(mockConsolePrinter).printLine("User not found! User Id must be in format XXXX-XXXX where X is a number");
        orderVerifier.verify(mockConsolePrinter).printLine("Please enter User Id: ");
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockLibrary).getUsers();
        orderVerifier.verify(mockConsolePrinter).printLine("Please enter password: ");
        orderVerifier.verify(mockConsoleReader).getNextLine();
        Assert.assertEquals(mockUser, result);
    }

    @Test
    public void CallingLogInUserWithIncorrectPasswordWillPromptForReAttempt(){
        when(mockConsoleReader.getNextLine()).thenReturn( CORRECT_USER_ID, "P#ssW0rd",CORRECT_PASSWORD);
        User result = userValidator.logInUser();
        orderVerifier.verify(mockConsolePrinter).printLine("Please enter User Id: ");
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockLibrary).getUsers();
        orderVerifier.verify(mockConsolePrinter).printLine("Please enter password: ");
        orderVerifier.verify(mockConsoleReader).getNextLine();
        orderVerifier.verify(mockConsolePrinter).printLine("Incorrect password");
        orderVerifier.verify(mockConsolePrinter).printLine("Please enter password: ");
        orderVerifier.verify(mockConsoleReader).getNextLine();
        Assert.assertEquals(mockUser, result);
    }
}
