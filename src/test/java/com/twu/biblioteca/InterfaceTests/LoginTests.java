package com.twu.biblioteca.InterfaceTests;

import com.twu.biblioteca.MenuOptions.Login;
import com.twu.biblioteca.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class LoginTests {
    InOrder orderVerifier;
    Login login;

    @Mock
    UserValidator mockUserValidator;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        orderVerifier = inOrder(mockUserValidator);
        login = new Login(mockUserValidator);
    }

    @Test
    public void LoggedOutUserCanLogIn(){
        when(mockUserValidator.userIsLoggedIn()).thenReturn(false);
        login.executeOption();
        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).logInUser();
    }
    @Test
    public void LoggedInUserCanLogOut(){
        when(mockUserValidator.userIsLoggedIn()).thenReturn(true);
        login.executeOption();
        orderVerifier.verify(mockUserValidator, times(1)).userIsLoggedIn();
        orderVerifier.verify(mockUserValidator, times(1)).logOutUser();
    }
}
