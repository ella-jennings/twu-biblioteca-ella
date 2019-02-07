package com.twu.biblioteca;

import org.junit.Assert;
import org.junit.Test;

import java.util.Scanner;

public class ConsoleReaderTests {

    @Test
    public void CallingGetInputShouldReturnNextLineOnly(){
        ConsoleReader consoleReader = new ConsoleReader(new Scanner("your\ntest\ninput\n"));
        String result = consoleReader.getNextLine();
        Assert.assertEquals( "your", result);
        result = consoleReader.getNextLine();
        Assert.assertEquals( "test", result);
        result = consoleReader.getNextLine();
        Assert.assertEquals( "input", result);
    }

}
