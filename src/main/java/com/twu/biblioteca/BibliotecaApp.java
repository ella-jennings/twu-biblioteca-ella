package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {

    public static void main(String[] args) throws IOException {
        Book book1 = new Book(1,"Dark Places", "Gillian", "Flynn", 2011);
        Book book2 = new Book(2,"Talent Is Overrated", "Geoff", "Colvin", 2008);
        Book book3 = new Book(3, "Factfulness", "Hans", "Rosling", 2018);
        Movie movie1 = new Movie(4,"Die Hard", 1990, "Director");
        Movie movie2 = new Movie(5,"Die Hard 2", 1992, "Director", 7);
        User user1 = new User("1234-5678", "password1", "User 1 name", "user1@gmail.com", "+447949494010");
        User user2 = new User("8765-4321", "password2", "User 2 name", "user2@hotmail.com", "(628) 458 9956");

        Library library = new Library(Arrays.asList(book1, book2, book3, movie1, movie2), Arrays.asList(user1, user2));
        Scanner scanner = new Scanner(System.in);
        ConsolePrinter consolePrinter = new ConsolePrinter(System.out);
        ConsoleReader consoleReader = new ConsoleReader(scanner);
        ConsoleTerminator consoleTerminator = new ConsoleTerminator();
        UserValidator userValidator = new UserValidator(consoleReader, consolePrinter, library);
        new Console(library, consolePrinter, consoleReader, consoleTerminator, userValidator).processUserInput();
    }

}
