package com.twu.biblioteca.InterfaceTests;

import com.twu.biblioteca.*;
import com.twu.biblioteca.MenuOptions.GetDetails;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class GetDetailsTests {
    InOrder orderVerifier;
    GetDetails getDetails;
    private static final String ERROR_MESSAGE = "Please select a valid option!";

    @Mock
    ConsolePrinter mockConsolePrinter;
    @Mock
    UserValidator mockUserValidator;
    @Mock
    Library mockLibrary;
    @Mock
    User mockUser;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        getDetails = new GetDetails(mockLibrary, mockConsolePrinter, mockUserValidator);
        orderVerifier = inOrder(mockLibrary, mockConsolePrinter, mockUserValidator);
    }

    @Test
    public void LoggedInUserCanSeeTheirDetails() {
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        when(mockUserValidator.getCurrentUser()).thenReturn(mockUser);
        when(mockLibrary.getUserInformation(mockUser)).thenReturn("user info");
        getDetails.executeOption();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockLibrary, times(1)).getUserInformation(mockUser);
        orderVerifier.verify(mockConsolePrinter).printLine("user info");
    }

    @Test
    public void LoggedOutUserGetsErrorMessage() {
        when(mockUserValidator.userIsLoggedIn()).thenReturn(false);
        getDetails.executeOption();

        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockConsolePrinter, times(1)).printLine(ERROR_MESSAGE);
    }
}
