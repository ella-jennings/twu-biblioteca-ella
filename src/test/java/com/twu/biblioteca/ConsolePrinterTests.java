package com.twu.biblioteca;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsolePrinterTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }
    ConsolePrinter consolePrinter = new ConsolePrinter();

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    public void PrintLineShouldOutputTextWithNewLineAppended() {

        consolePrinter.printLine("print me");

        Assert.assertEquals(outContent.toString(), "print me\n");
    }

    @Test
    public void PrintShouldOutputTextWithNoNewLineAppended() {
        consolePrinter.print("this\nis\na\ntest");

        Assert.assertEquals(outContent.toString(), "this\nis\na\ntest");
    }
}
