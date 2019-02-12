package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;
import com.twu.biblioteca.MenuOptions.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

class Console {
    private final ConsolePrinter consolePrinter;
    private ConsoleReader consoleReader;
    private ConsoleTerminator consoleTerminator;
    private ConsoleHelper consoleHelper;
    private UserValidator userValidator;
    private Library library;
    private Map<String, IMenuOption> menuOptionMap;
    private Map<String, ILogInMenuItem> checkInMenuOptionMap;
    private static final String ERROR_MESSAGE = "Please select a valid option!";
    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!";
    private User loggedInUser = null;


    Console(Library library, ConsolePrinter consolePrinter, ConsoleReader reader, ConsoleTerminator consoleTerminator, ConsoleHelper consoleHelper, UserValidator userValidator) {
        this.library = library;
        this.consolePrinter = consolePrinter;
        this.consoleReader = reader;
        this.consoleTerminator = consoleTerminator;
        this.consoleHelper = consoleHelper;
        this.userValidator = userValidator;
        setUpOptions(library, consolePrinter, consoleHelper);
        this.consolePrinter.printLine(WELCOME_MESSAGE);
        String menu = consoleHelper.getMenu(loggedInUser);
        this.consolePrinter.print(menu);
    }

    private void setUpOptions(Library library, ConsolePrinter consolePrinter, ConsoleHelper consoleHelper) {
        menuOptionMap = new LinkedHashMap<String, IMenuOption>(){
            {
                put("1", new ListItems(library, consolePrinter, Book.class));
                put("4", new ListItems(library, consolePrinter, Movie.class));
                put("Q", new Quit(consoleTerminator));
            }
        };
        checkInMenuOptionMap = new LinkedHashMap<String, ILogInMenuItem>(){
            {
                put("2", new CheckOutItem(library, consolePrinter, Book.class, consoleHelper));
                put("3", new ReturnItem(library, consolePrinter, Book.class, consoleHelper));
                put("5", new CheckOutItem(library, consolePrinter, Movie.class, consoleHelper));
                put("6", new ReturnItem(library, consolePrinter, Movie.class, consoleHelper));
            }
        };
    }

    void processUserInput() throws IOException {
        String userInput = consoleReader.getNextLine();
        if(menuOptionMap.containsKey(userInput)){
            menuOptionMap.get(userInput).executeOption();
            if(!userInput.equals("Q")){
                returnToMenu();
            }
        } else if (checkInMenuOptionMap.containsKey(userInput)){
            loggedInUser = userValidator.logInUserIfNotAlready(loggedInUser);
            if(loggedInUser == null){
                returnToMenu();
            } else {
                checkInMenuOptionMap.get(userInput).executeOption(loggedInUser);
                returnToMenu();
            }
        } else if(userInput.equals("L")) {
            if(loggedInUser == null){
                loggedInUser = userValidator.logInUserIfNotAlready(loggedInUser);
                returnToMenu();
            } else {
                loggedInUser = null;
                returnToMenu();
            }
        } else if(userInput.equals("D")){
            if(loggedInUser == null){
                getErrorMessageAndCheckInput();
            } else {
                consolePrinter.printLine(library.getUserInformation(loggedInUser));
                returnToMenu();
            }
        } else {
            getErrorMessageAndCheckInput();
        }
    }

    private void getErrorMessageAndCheckInput() throws IOException {
        consolePrinter.printLine(ERROR_MESSAGE);
        processUserInput();
    }


    private void returnToMenu() throws IOException {
        consolePrinter.print(consoleHelper.getMenu(loggedInUser));
        processUserInput();
    }
}
