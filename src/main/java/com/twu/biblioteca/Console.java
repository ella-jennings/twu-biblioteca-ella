package com.twu.biblioteca;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Console {
    private final ConsolePrinter consolePrinter;
    private ConsoleReader reader;
    private ConsoleTerminator consoleTerminator;
    private Library library;
    private enum menu {
        BOOKS("1"), CHECKOUT_BOOK("2"), RETURN_BOOK("3"), QUIT("Q");

        public String getCode() {
            return code;
        }

        private String code;

        menu(String input) {
            code = input;
        }
    }
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
    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!";
    private String menuOptions;


    Console(Library library, ConsolePrinter consolePrinter, ConsoleReader reader, ConsoleTerminator consoleTerminator) {
        this.library = library;
        this.consolePrinter = consolePrinter;
        this.reader = reader;
        this.consoleTerminator = consoleTerminator;
        getMenuOptions();
        this.consolePrinter.printLine(WELCOME_MESSAGE);
        this.consolePrinter.print(menuOptions);
    }

    void processUserInput() throws IOException {
        String userInput = reader.getNextLine();

        if (userInput.equals(menu.BOOKS.getCode())) {
            consolePrinter.print(library.getBookInformation());
            returnToMenu();
        }
        else if (userInput.equals(menu.CHECKOUT_BOOK.getCode())){
            String userResponse = getBookTitleFromUser("check out");
            consolePrinter.printLine(library.checkOut(userResponse));
            returnToMenu();
        }
        else if (userInput.equals(menu.RETURN_BOOK.getCode())){
            String userResponse = getBookTitleFromUser("return");
            consolePrinter.printLine(library.returnBook(userResponse));
            returnToMenu();
        }
        else if(userInput.equals(menu.QUIT.getCode())) {
            consoleTerminator.exitApplication();
        }
        else {
            consolePrinter.printLine(ERROR_MESSAGE);
            processUserInput();
        }
    }

    private void getMenuOptions() {
        StringBuilder options = new StringBuilder();
        for(Map.Entry<String, String> entry: MENU_OPTIONS.entrySet()){
            options.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }
        menuOptions = options.toString();
    }

    private void returnToMenu() throws IOException {
        consolePrinter.print(menuOptions);
        processUserInput();
    }

    private String getBookTitleFromUser(String function) {
        consolePrinter.printLine("Enter book title to " + function + ":");
        return reader.getNextLine();
    }
}
