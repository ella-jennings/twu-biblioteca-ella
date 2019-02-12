package com.twu.biblioteca.MenuOptions;

import com.twu.biblioteca.*;

public class ReturnItem implements ILogInMenuItem {
    private final Library library;
    private final ConsolePrinter consolePrinter;
    private final Class typeOfItem;
    private final ConsoleHelper consoleHelper;

    public ReturnItem(Library library, ConsolePrinter consolePrinter, Class typeOfItem, ConsoleHelper consoleHelper) {
        this.library = library;
        this.consolePrinter = consolePrinter;
        this.typeOfItem = typeOfItem;
        this.consoleHelper = consoleHelper;
    }

    public void executeOption(User user) {
        String userResponse = consoleHelper.getItemTitleFromUser("return", typeOfItem);
        String messageToReturn = library.returnItem(typeOfItem, userResponse, user);
        consolePrinter.printLine(messageToReturn);
    }
}
