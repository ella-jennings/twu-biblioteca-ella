package com.twu.biblioteca.Interfaces;

import com.twu.biblioteca.Library;

public class ListBooks implements IMenuOption {
    private Library library;

    public ListBooks(Library library) {
        this.library = library;
    }

    @Override
    public String executeOption() {
        String string = library.getBookInformation();
        return string;
    }
}
