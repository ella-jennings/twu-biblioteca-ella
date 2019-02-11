package com.twu.biblioteca.MenuOptions;

import com.twu.biblioteca.Book;
import com.twu.biblioteca.Library;

public class ListBooks implements IMenuOption {
    private Library library;

    public ListBooks(Library library) {
        this.library = library;
    }

    @Override
    public String executeOption() {
        String string = library.getInformation(Book.class);
        return string;
    }
}
