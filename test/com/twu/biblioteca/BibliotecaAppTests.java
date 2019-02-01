package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BibliotecaAppTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    public void StartingApplicationShouldDisplayWelcomeMessage() {
        new BibliotecaApp().main(null);

        assertEquals("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!\n", outContent.toString());
    }

    @Test
    public void StartingApplicationShouldDisplayListOfBooks() {
        List<String> listOfBooks = Arrays.asList("Dark Places", "Talent Is Overrated", "Factfulness", "Slow Horses", "Sweet Little Lies", "In Order To Live", "The Couple Next Door");
        new BibliotecaApp().main(null);
        String output[] = outContent.toString().split("\n");

        assertEquals(8, output.length);
        assertEquals("Dark Places", output[1]);
        assertEquals("The Couple Next Door", output[7]);
    }
}
