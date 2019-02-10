package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class BibliotecaApp {

    public static void main(String[] args) throws IOException {
        Book book1 = new Book("Dark Places", "Gillian", "Flynn", 2011);
        Book book2 = new Book("Talent Is Overrated", "Geoff", "Colvin", 2008);
        Book book3 = new Book("Factfulness", "Hans", "Rosling", 2018);
        Movie movie1 = new Movie("Die Hard", 1990, "Director");
        Movie movie2 = new Movie("Die Hard 2", 1992, "Director", 7);

        Library library = new Library(Arrays.asList(book1, book2, book3, movie1, movie2));
        Scanner scanner = new Scanner(System.in);
        ConsolePrinter consolePrinter = new ConsolePrinter(System.out);
        ConsoleReader reader = new ConsoleReader(scanner);
        ConsoleTerminator consoleTerminator = new ConsoleTerminator();
        new Console(library, consolePrinter, reader, consoleTerminator).processUserInput();
    }

}
