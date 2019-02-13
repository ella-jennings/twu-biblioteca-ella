package com.twu.biblioteca;

import com.twu.biblioteca.MenuOptions.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

class Console {
    private final ConsolePrinter consolePrinter;
    private ConsoleReader consoleReader;
    private ConsoleHelper consoleHelper;
    private UserValidator userValidator;
    private final ListItems listBook;
    private final ListItems listMovie;
    private final CheckOutItem checkOutBook;
    private final CheckOutItem checkOutMovie;
    private final ReturnItem returnBook;
    private final ReturnItem returnMovie;
    private GetDetails getDetails;
    private Quit quit;
    private Map<String, IMenuOption> menuOptionMap;
    private static final String ERROR_MESSAGE = "Please select a valid option!";
    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!";


    Console(ConsolePrinter consolePrinter, ConsoleReader reader, ConsoleHelper consoleHelper, UserValidator userValidator, ListItems listBook, ListItems listMovie, CheckOutItem checkOutBook, CheckOutItem checkOutMovie, ReturnItem returnBook, ReturnItem returnMovie, GetDetails getDetails, Quit quit) {
        this.consolePrinter = consolePrinter;
        this.consoleReader = reader;
        this.consoleHelper = consoleHelper;
        this.userValidator = userValidator;
        this.listBook = listBook;
        this.listMovie = listMovie;
        this.checkOutBook = checkOutBook;
        this.checkOutMovie = checkOutMovie;
        this.returnBook = returnBook;
        this.returnMovie = returnMovie;
        this.getDetails = getDetails;
        this.quit = quit;
        setUpOptions();
        this.consolePrinter.printLine(WELCOME_MESSAGE);
        String menu = consoleHelper.getMenu(userValidator.userIsLoggedIn());
        this.consolePrinter.print(menu);
    }

    private void setUpOptions() {
        menuOptionMap = new LinkedHashMap<String, IMenuOption>(){
            {
                put("1", listBook);
                put("2", checkOutBook);
                put("3", returnBook);
                put("4", listMovie);
                put("5", checkOutMovie);
                put("6", returnMovie);
                put("L", new Login(userValidator));
                put("D", getDetails);
                put("Q", quit);
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
        } else {
            getErrorMessage();
        }
    }

    private void getErrorMessage() throws IOException {
        consolePrinter.printLine(ERROR_MESSAGE);
        returnToMenu();
    }


    private void returnToMenu() throws IOException {
        consolePrinter.print(consoleHelper.getMenu(userValidator.userIsLoggedIn()));
        processUserInput();
    }
}
