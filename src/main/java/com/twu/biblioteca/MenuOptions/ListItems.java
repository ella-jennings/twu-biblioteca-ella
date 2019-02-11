package com.twu.biblioteca.MenuOptions;

import com.twu.biblioteca.ConsolePrinter;
import com.twu.biblioteca.Library;

public class ListItems implements IMenuOption {
    private Library library;
    private ConsolePrinter consolePrinter;
    private Class typeOfItem;

    public ListItems(Library library, ConsolePrinter consolePrinter, Class typeOfItem) {
        this.library = library;
        this.consolePrinter = consolePrinter;
        this.typeOfItem = typeOfItem;
    }

    public void executeOption() {
        consolePrinter.printLine(library.getInformation(typeOfItem));
    }
}
