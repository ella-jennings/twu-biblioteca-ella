package com.twu.biblioteca;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Console {
    private final ConsolePrinter consolePrinter;
    private ConsoleReader reader;
    private Library library;
    private static final Map<String, String> MENU_OPTIONS = new LinkedHashMap<String, String>() {
        {
            put("1", "List Of Books");
            put("2", "Checkout a Book");
            put("3", "Return a Book");
            put("4", "List Of Movies");
            put("Q", "Quit");
        }
    };
    private static final String ERROR_MESSAGE = "Please select a valid option!";
    private String menuOptions;

    Console(Library library, ConsolePrinter consolePrinter, ConsoleReader reader) throws IOException {
        this.library = library;
        this.consolePrinter = consolePrinter;
        this.reader = reader;
        this.consolePrinter.printLine("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
        getMenuOptions();
        this.consolePrinter.print(menuOptions);
    }

    void processUserInput() throws IOException {
        String userInput = reader.getNextLine();

        if (userInput.equals("1")) {
            consolePrinter.print(library.getBookInformation());
            consolePrinter.print(menuOptions);
        }
        else if (userInput.equals("2")){
            String userResponse = getBookTitleFromUser("check out");
            consolePrinter.printLine(library.checkOut(userResponse));
            consolePrinter.print(menuOptions);
        }
        else if (userInput.equals("3")){
            String userResponse = getBookTitleFromUser("return");
            consolePrinter.printLine(library.returnBook(userResponse));
            consolePrinter.print(menuOptions);
        }
//        if (userInput.equals("4")){
//            consolePrinter.print(library.getMovieInformation());
//            consolePrinter.print(menuOptions);
//        }
        else if(userInput.equals("Q")) {
            System.exit(0);
        }
        else {
            consolePrinter.printLine(ERROR_MESSAGE);
        }
    }

    private void getMenuOptions() {
        StringBuilder options = new StringBuilder();
        for(Map.Entry<String, String> entry: MENU_OPTIONS.entrySet()){
            options.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }
        menuOptions = options.toString();
    }

    private String getBookTitleFromUser(String function) throws IOException {
        consolePrinter.printLine("Enter book title to " + function + ":");
        return reader.getNextLine();
    }
}
