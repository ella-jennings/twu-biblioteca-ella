package com.twu.biblioteca.MenuOptions;

import com.twu.biblioteca.*;

public class CheckOutItem implements ICheckOutMenuOption {
    private final Library library;
    private final ConsolePrinter consolePrinter;
    private final Class typeOfItem;
    private ConsoleHelper consoleHelper;

    public CheckOutItem(Library library, ConsolePrinter consolePrinter, Class typeOfItem, ConsoleHelper consoleHelper) {

        this.library = library;
        this.consolePrinter = consolePrinter;
        this.typeOfItem = typeOfItem;
        this.consoleHelper = consoleHelper;
    }

    public void executeOption(User user) {
        String userResponse = consoleHelper.getItemTitleFromUser("check out", typeOfItem);
        consolePrinter.printLine(library.checkOutItem(Book.class, userResponse, user));
    }
}
