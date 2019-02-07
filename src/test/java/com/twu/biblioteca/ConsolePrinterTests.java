package com.twu.biblioteca;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsolePrinterTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ConsolePrinter consolePrinter;

    @Before
    public void setUp() {
        consolePrinter = new ConsolePrinter(new PrintStream(outContent));
    }

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
