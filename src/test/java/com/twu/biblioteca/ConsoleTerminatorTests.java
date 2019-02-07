package com.twu.biblioteca;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class ConsoleTerminatorTests {
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void CallingExitApplicationWillExitTheProgram(){
        ConsoleTerminator consoleTerminator = new ConsoleTerminator();
        exit.expectSystemExitWithStatus(0);
        consoleTerminator.exitApplication();
    }
}
