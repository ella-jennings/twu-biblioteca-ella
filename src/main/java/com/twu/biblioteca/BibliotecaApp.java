package com.twu.biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BibliotecaApp {

    public static void main(String[] args) throws IOException {
        Library library = new Library();
        ConsolePrinter consolePrinter = new ConsolePrinter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Console(library, consolePrinter, reader).ProcessUserInput();
    }

}
