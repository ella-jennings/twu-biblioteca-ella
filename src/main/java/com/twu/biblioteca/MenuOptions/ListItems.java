package com.twu.biblioteca.MenuOptions;

import com.twu.biblioteca.Library;

public class ListItems implements IMenuOption {
    private Library library;
    private Class typeOfItem;

    public ListItems(Library library, Class typeOfItem) {
        this.library = library;
        this.typeOfItem = typeOfItem;
    }

    @Override
    public String executeOption() {
        String string = library.getInformation(typeOfItem);
        return string;
    }
}
