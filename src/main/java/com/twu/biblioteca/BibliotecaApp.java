package com.twu.biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class BibliotecaApp {

    public static void main(String[] args) throws IOException {
        Book book1 = new Book("Dark Places", "Gillian", "Flynn", 2011);
        Book book2 = new Book("Talent Is Overrated", "Geoff", "Colvin", 2008);
        Book book3 = new Book("Factfulness", "Hans", "Rosling", 2018);

        Library library = new Library(Arrays.asList(book1, book2, book3));
        ConsolePrinter consolePrinter = new ConsolePrinter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Console(library, consolePrinter, reader).processUserInput();
    }

}
