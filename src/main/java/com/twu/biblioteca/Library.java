package com.twu.biblioteca;

import java.util.Arrays;
import java.util.List;

public class Library implements ILibrary {

    static Book book1 = new Book("Dark Places", "Gillian", "Flynn", "2011");
    static Book book2 = new Book("Talent Is Overrated", "Geoff", "Colvin", "2008");
    static Book book3 = new Book("Factfulness", "Hans", "Rosling", "2018");
    private List<Book> listOfBooks;

    public Library() {
        listOfBooks = Arrays.asList(book1, book2, book3);
    }


    public String getBookInformation() {
        String bookInformation = "";
        for(Book book: listOfBooks) bookInformation += String.format(book.getBookInformation() + "\n");
        return bookInformation;
    }
}

