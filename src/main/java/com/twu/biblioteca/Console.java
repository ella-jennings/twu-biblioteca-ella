package com.twu.biblioteca;

import java.io.BufferedReader;
import java.io.IOException;

public class Console {
    private final ConsolePrinter consolePrinter;
    private BufferedReader reader;
    private ILibrary library;

    public Console(ILibrary library, ConsolePrinter consolePrinter, BufferedReader reader) {
        this.library = library;
        this.consolePrinter = consolePrinter;
        this.reader = reader;
        this.consolePrinter.printLine("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
        this.consolePrinter.printLine(getMenuOptions());
    }

    public String getMenuOptions() {
        return "1 - List Of Books";
    }

    public void ProcessUserInput() throws IOException {
        String userInput = reader.readLine();

        if(userInput.equals("1")){
            consolePrinter.print(library.getBookInformation());
        }
    }
}
