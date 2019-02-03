package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String[] args) {
        Library library = new Library();
        ConsolePrinter consolePrinter = new ConsolePrinter();
        Console console = new Console(library, consolePrinter);
        console.ProcessUserInput(1);
    }

}
