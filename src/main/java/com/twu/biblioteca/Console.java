package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;
import com.twu.biblioteca.MenuOptions.IMenuOption;
import com.twu.biblioteca.MenuOptions.ListBooks;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Console {
    private final ConsolePrinter consolePrinter;
    private ConsoleReader consoleReader;
    private ConsoleTerminator consoleTerminator;
    private ConsoleHelper consoleHelper;
    private UserValidator userValidator;
    private Library library;
    private enum functions {
        CHECK_OUT("check out"), RETURN("return");

        private String textString;

        functions(String textString) {
            this.textString = textString;
        }

        public String getTextString() {
            return textString;
        }
    }
    private Map<String, IMenuOption> menuOptionMap;
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
        menuOptionMap = new LinkedHashMap<String, IMenuOption>(){
            {put("1", new ListBooks(library));
            }
        };
        this.consolePrinter.printLine(WELCOME_MESSAGE);
        String menu = consoleHelper.getMenu(loggedInUser);
        this.consolePrinter.print(menu);
    }

    void processUserInput() throws IOException {
        String userInput = consoleReader.getNextLine();
        if(userInput.equals("1")){
            for(Map.Entry<String, IMenuOption> option: menuOptionMap.entrySet()){
                if(userInput.equals(option.getKey())){
                    String stringToPrint = option.getValue().executeOption();
                    consolePrinter.printLine(stringToPrint);
                    returnToMenu();
                }
            }
        }
        else if (userInput.equals("2")){
            tryLogIn();
            String userResponse = getItemTitleFromUser(functions.CHECK_OUT, Book.class);
            consolePrinter.printLine(library.checkOutItem(Book.class, userResponse, loggedInUser));
            returnToMenu();
        }
        else if (userInput.equals("3")){
            tryLogIn();
            String userResponse = getItemTitleFromUser(functions.RETURN, Book.class);
            consolePrinter.printLine(library.returnItem(Book.class, userResponse, loggedInUser));
            returnToMenu();
        }
        else if (userInput.equals("4")){
            consolePrinter.printLine(library.getInformation(Movie.class));
            returnToMenu();
        }
        else if (userInput.equals("5")){
            tryLogIn();
            String userResponse = getItemTitleFromUser(functions.CHECK_OUT, Movie.class);
            consolePrinter.printLine(library.checkOutItem(Movie.class, userResponse, loggedInUser));
            returnToMenu();
        }
        else if (userInput.equals("6")){
            tryLogIn();
            String userResponse = getItemTitleFromUser(functions.RETURN, Movie.class);
            consolePrinter.printLine(library.returnItem(Movie.class, userResponse, loggedInUser));
            returnToMenu();
        }
        else if(userInput.equals("L")) {
            if(loggedInUser == null){
            tryLogIn();
            returnToMenu();
            } else {
                loggedInUser = null;
                returnToMenu();
            }
        }
        else if(userInput.equals("D")){
            if(loggedInUser == null){
                getErrorMessageAndCheckInput();
            } else {
                consolePrinter.printLine(library.getUserInformation(loggedInUser));
                returnToMenu();
            }
        }
        else if(userInput.equals("Q")) {
            consoleTerminator.exitApplication();
        }
        else {
            getErrorMessageAndCheckInput();
        }
    }

    private void getErrorMessageAndCheckInput() throws IOException {
        consolePrinter.printLine(ERROR_MESSAGE);
        processUserInput();
    }

    private void tryLogIn() {
        if(loggedInUser == null) {
            loggedInUser = userValidator.logInUser();
        }
    }

    private void returnToMenu() throws IOException {
        consolePrinter.print(consoleHelper.getMenu(loggedInUser));
        processUserInput();
    }

    private <T> String getItemTitleFromUser(functions function, Class<T> typeOfItem) {
        consolePrinter.printLine("Enter " + typeOfItem.getSimpleName().toLowerCase() + " title or id to " + function.getTextString() + ":");
        return consoleReader.getNextLine();
    }
}
