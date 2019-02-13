package com.twu.biblioteca.MenuOptions;

import com.twu.biblioteca.*;

public class ReturnItem implements IMenuOption {
    private final Library library;
    private final ConsolePrinter consolePrinter;
    private final Class typeOfItem;
    private final ConsoleHelper consoleHelper;
    private UserValidator userValidator;

    public ReturnItem(Library library, ConsolePrinter consolePrinter, Class typeOfItem, ConsoleHelper consoleHelper, UserValidator userValidator) {
        this.library = library;
        this.consolePrinter = consolePrinter;
        this.typeOfItem = typeOfItem;
        this.consoleHelper = consoleHelper;
        this.userValidator = userValidator;
    }

    public void executeOption() {
        if(!userValidator.userIsLoggedIn()) {
            userValidator.logInUser();
            if(userValidator.userIsLoggedIn()){
                returnItem();
            }
        } else {
            returnItem();
        }
    }

    private void returnItem() {
        User user = userValidator.getCurrentUser();
        String userResponse = consoleHelper.getItemTitleFromUser("return", typeOfItem);
        String messageToReturn = library.returnItem(typeOfItem, userResponse, user);
        consolePrinter.printLine(messageToReturn);
    }
}
