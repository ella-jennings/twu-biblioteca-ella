package com.twu.biblioteca;

import java.util.Arrays;
import java.util.List;

public class Library implements ILibrary {

    private static Book book1 = new Book("Dark Places", "Gillian", "Flynn", "2011");
    private static Book book2 = new Book("Talent Is Overrated", "Geoff", "Colvin", "2008");
    private static Book book3 = new Book("Factfulness", "Hans", "Rosling", "2018");
    private List<Book> listOfBooks;

    Library() {
        listOfBooks = Arrays.asList(book1, book2, book3);
    }


    public String getBookInformation() {
        StringBuilder bookInformation = new StringBuilder();
        for(Book book: listOfBooks) bookInformation.append(book.getBookInformation()).append("\n");
        return bookInformation.toString();
    }
}

