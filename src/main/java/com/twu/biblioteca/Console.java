package com.twu.biblioteca;

public class Console {
    private ILibrary library;

    public Console(ILibrary library) {

        this.library = library;
    }

    public String getWelcomeMessage() {
        return "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!";
    }

    public String getMenuOptions() {
        return "1 - List Of Books";
    }

    public String ProcessUserInput(int i) {
        return library.displayBookInformation();
    }
}
