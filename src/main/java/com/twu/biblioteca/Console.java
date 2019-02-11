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
    private String menuOptions;
    private User loggedInUser = null;


    Console(Library library, ConsolePrinter consolePrinter, ConsoleReader reader, ConsoleTerminator consoleTerminator, UserValidator userValidator) {
        this.library = library;
        this.consolePrinter = consolePrinter;
        this.consoleReader = reader;
        this.consoleTerminator = consoleTerminator;
        this.userValidator = userValidator;
        getMenuOptions();
        menuOptionMap = new LinkedHashMap<String, IMenuOption>(){
            {put("1", new ListBooks(library));
            }
        };
        this.consolePrinter.printLine(WELCOME_MESSAGE);
        this.consolePrinter.print(menuOptions);
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
            consolePrinter.printLine(library.checkOutBook(userResponse, loggedInUser));
            returnToMenu();
        }
        else if (userInput.equals("3")){
            tryLogIn();
            String userResponse = getItemTitleFromUser(functions.RETURN, Book.class);
            consolePrinter.printLine(library.returnBook(userResponse, loggedInUser));
            returnToMenu();
        }
        else if (userInput.equals("4")){
            consolePrinter.printLine(library.getMovieInformation());
            returnToMenu();
        }
        else if (userInput.equals("5")){
            tryLogIn();
            String userResponse = getItemTitleFromUser(functions.CHECK_OUT, Movie.class);
            consolePrinter.printLine(library.checkOutMovie(userResponse, loggedInUser));
            returnToMenu();
        }
        else if (userInput.equals("6")){
            tryLogIn();
            String userResponse = getItemTitleFromUser(functions.RETURN, Movie.class);
            consolePrinter.printLine(library.returnMovie(userResponse, loggedInUser));
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

    private void getMenuOptions() {
        Map<String, String> menuOptions = new LinkedHashMap<>();
        menuOptions.put("1", "List Of Books");
        menuOptions.put("2", "Checkout a Book");
        menuOptions.put("3", "Return a Book");
        menuOptions.put("4", "List Of Movies");
        menuOptions.put("5", "Checkout a Movie");
        menuOptions.put("6", "Return a Movie");
        if(loggedInUser != null){
            menuOptions.put("D", "View my details");
            menuOptions.put("L", "Log out");
        } else {
            menuOptions.put("L", "Login");
        }
        menuOptions.put("Q", "Quit");

        StringBuilder options = new StringBuilder();
        for(Map.Entry<String, String> entry: menuOptions.entrySet()){
            options.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }
        this.menuOptions = options.toString();
    }

    private void returnToMenu() throws IOException {
        getMenuOptions();
        consolePrinter.print(menuOptions);
        processUserInput();
    }

    private <T> String getItemTitleFromUser(functions function, Class<T> typeOfItem) {
        consolePrinter.printLine("Enter " + typeOfItem.getSimpleName().toLowerCase() + " title or id to " + function.getTextString() + ":");
        return consoleReader.getNextLine();
    }
}
