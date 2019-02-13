package com.twu.biblioteca.InterfaceTests;

import com.twu.biblioteca.ConsoleTerminator;
import com.twu.biblioteca.MenuOptions.Quit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class QuitTests {
    @Mock
    ConsoleTerminator mockConsoleTerminator;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void UserCanQuitApplication(){
        Quit quit = new Quit(mockConsoleTerminator);
        quit.executeOption();
        verify(mockConsoleTerminator, times(1)).exitApplication();
    }
}
