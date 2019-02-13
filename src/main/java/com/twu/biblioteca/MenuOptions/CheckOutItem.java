package com.twu.biblioteca.MenuOptions;

import com.twu.biblioteca.*;

public class CheckOutItem implements IMenuOption {
    private final Library library;
    private final ConsolePrinter consolePrinter;
    private final Class typeOfItem;
    private ConsoleHelper consoleHelper;
    private UserValidator userValidator;

    public CheckOutItem(Library library, ConsolePrinter consolePrinter, Class typeOfItem, ConsoleHelper consoleHelper, UserValidator userValidator) {

        this.library = library;
        this.consolePrinter = consolePrinter;
        this.typeOfItem = typeOfItem;
        this.consoleHelper = consoleHelper;
        this.userValidator = userValidator;
    }

    public void executeOption() {
        if(!userValidator.userIsLoggedIn()) {
            userValidator.logInUser();
            if (userValidator.userIsLoggedIn()) {
                checkOut();
            }
        } else {
            checkOut();
        }

    }

    private void checkOut() {
        User user = userValidator.getCurrentUser();
        String userResponse = consoleHelper.getItemTitleFromUser("check out", typeOfItem);
        String messageToPrint = library.checkOutItem(typeOfItem, userResponse, user);
        consolePrinter.printLine(messageToPrint);
    }
}
