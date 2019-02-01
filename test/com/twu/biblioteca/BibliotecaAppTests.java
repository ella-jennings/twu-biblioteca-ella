package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
    public void StartingApplicationShouldDisplayWelcome() {
        new BibliotecaApp().main(null);
        String output[] = outContent.toString().split("\n");
        assertEquals("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!", output[0]);
    }

//    @Test
//    public void StartingApplicationShouldDisplayMenuOptions() {
//        new BibliotecaApp().main(null);
//        String output[] = outContent.toString().split("\n");
//
//        assertEquals("1 - List Of Books", output[1]);
//    }


    @Test
    public void StartingApplicationShouldDisplayListOfBooks() {
        new BibliotecaApp().main(null);
        String output[] = outContent.toString().split("\n");

        assertEquals(4, output.length);
        assertEquals("Dark Places | Flynn, G | 2011", output[1]);
        assertEquals("Talent Is Overrated | Colvin, G | 2008", output[2]);
        assertEquals("Factfulness | Rosling, H | 2018", output[3]);
    }

}
