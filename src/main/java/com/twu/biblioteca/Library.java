package com.twu.biblioteca;

import java.util.Arrays;
import java.util.List;

class Library {

    private final List<Book> listOfBooks;

    Library(List<Book> listOfBooks) {
        this.listOfBooks = listOfBooks;
    }


    String getBookInformation() {
        StringBuilder bookInformation = new StringBuilder();
        for(Book book: listOfBooks) {
            if(!book.getOnLoan()){
                bookInformation.append(book.getBookInformation()).append("\n");
            }
        }
        return bookInformation.toString();
    }

    void checkOut(String bookName) {
        for(Book book: listOfBooks){
            if(book.getTitle().equals(bookName)){
                book.checkOut();
            }
        }
    }
}

