package com.twu.biblioteca;

public class Console {
    private final ConsolePrinter consolePrinter;
    private ILibrary library;

    public Console(ILibrary library, ConsolePrinter consolePrinter) {
        this.library = library;
        this.consolePrinter = consolePrinter;
        this.consolePrinter.printLine("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
        this.consolePrinter.printLine(getMenuOptions());
    }

    public String getMenuOptions() {
        return "1 - List Of Books";
    }

    public String ProcessUserInput(int i) {
        return library.displayBookInformation();
    }
}
