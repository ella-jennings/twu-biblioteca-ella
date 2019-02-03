package com.twu.biblioteca;

import java.util.List;

class Library {

    private final List<Book> listOfBooks;

    Library(List<Book> listOfBooks) {
        this.listOfBooks = listOfBooks;
    }


    String getBookInformation() {
        StringBuilder bookInformation = new StringBuilder();
        for(Book book: listOfBooks) {
            if(!book.isOnLoan()){
                bookInformation.append(book.getBookInformation()).append("\n");
            }
        }
        return bookInformation.toString();
    }

    String checkOut(String bookName) {
        for(Book book: listOfBooks){
            if(book.getTitle().equals(bookName)){
                book.checkOut();
                return "Thank you! Enjoy the book";
            }
        }
        return "Sorry, that book is not available";
    }

    void returnBook(String bookName){
        for(Book book: listOfBooks){
            if(book.getTitle().equals(bookName)){
                book.returnBook();
            }
        }
    }
}

