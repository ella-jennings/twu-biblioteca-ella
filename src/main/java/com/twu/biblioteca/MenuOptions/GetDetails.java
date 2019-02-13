package com.twu.biblioteca.MenuOptions;

import com.twu.biblioteca.ConsolePrinter;
import com.twu.biblioteca.Library;
import com.twu.biblioteca.User;
import com.twu.biblioteca.UserValidator;

public class GetDetails implements IMenuOption {
    private final Library library;
    private final ConsolePrinter consolePrinter;
    private final UserValidator userValidator;
    private static final String ERROR_MESSAGE = "Please select a valid option!";


    public GetDetails(Library library, ConsolePrinter consolePrinter, UserValidator userValidator) {

        this.library = library;
        this.consolePrinter = consolePrinter;
        this.userValidator = userValidator;
    }

    public void executeOption() {
        if(!userValidator.userIsLoggedIn()){
            consolePrinter.printLine(ERROR_MESSAGE);
        } else {
            User user = userValidator.getCurrentUser();
            consolePrinter.printLine(library.getUserInformation(user));
        }
    }
}
