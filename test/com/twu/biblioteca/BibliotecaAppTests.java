package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BibliotecaAppTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    String[] book1 = {"Dark Places", "Gillian", "Flynn", "2011"};
    String[] book2 = {"Talent Is Overrated", "Geoff", "Colvin", "2008"};
    String[] book3 = {"Factfulness", "Hans", "Rosling", "2018"};
    private List<String[]> booksList = Arrays.asList(book1, book2, book3);

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    public void StartingApplicationShouldDisplayWelcomeAndListOfBooks() {
        new BibliotecaApp().main(null);
        String output[] = outContent.toString().split("\n");

        assertEquals(4, output.length);
        assertEquals("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!", output[0]);
        assertEquals("Dark Places", output[1]);
        assertEquals("The Couple Next Door", output[7]);
    }

    @Test
    public void BooksShouldContainTitleAuthorAndDateInformation() {
        Book book = new Book(book1[0], book1[1], book1[2], book1[3]);
        String result = book.getBookInformation();
        assertThat(result, is(equalTo("Dark Places | Flynn, G | 2011")));

    }
}
