package com.twu.biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Console {
    private final ConsolePrinter consolePrinter;
    private BufferedReader reader;
    private Library library;
    private static final Map<String, String> MENU_OPTIONS = new HashMap<String, String>() {
        {
            put("1", "List Of Books");
            put("Q", "Quit");
        }
    };
    private static final String ERROR_MESSAGE = "Please select a valid option!";

    Console(Library library, ConsolePrinter consolePrinter, BufferedReader reader) throws IOException {
        this.library = library;
        this.consolePrinter = consolePrinter;
        this.reader = reader;
        this.consolePrinter.printLine("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
        this.consolePrinter.print(getMenuOptions());
    }

    private String getMenuOptions() {
        StringBuilder options = new StringBuilder();
        for(Map.Entry<String, String> entry: MENU_OPTIONS.entrySet()){
            options.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }
        return options.toString();
    }

    void ProcessUserInput() throws IOException {
        String userInput = reader.readLine();

        if (userInput.equals("1")) {
            consolePrinter.print(library.getBookInformation());
            checkoutBook();
        }
        if(userInput.equals("Q")) {
            System.exit(0);
        }
        else {
            consolePrinter.printLine(ERROR_MESSAGE);
        }
    }

    private void checkoutBook() throws IOException {
        consolePrinter.printLine("Enter book title to check out:");
        String userInput = reader.readLine();
        consolePrinter.printLine(library.checkOut(userInput));
    }
}
