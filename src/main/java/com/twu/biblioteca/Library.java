package com.twu.biblioteca;

import java.util.List;

public class Library {

    private final List<Book> listOfBooks;
//    private final List<Movie> listOfMovies;

    Library(List<Book> listOfBooks) {
        this.listOfBooks = listOfBooks;
    }


    public String getBookInformation() {
        StringBuilder bookInformation = new StringBuilder();
        for(Book book: listOfBooks) {
            if(!book.isOnLoan()){
                bookInformation.append(displayBookInformation(book));
            }
        }
        return bookInformation.toString();
    }

    private String displayBookInformation(Book book) {
        String columnSeparator = " | ";
        return book.getTitle() + columnSeparator + book.getLastName() + ", " + book.getFirstName().substring(0,1) + columnSeparator + book.getPublishedDate() + "\n";
    }

    String checkOut(String bookName) {
        for(Book book: listOfBooks){
            if(book.getTitle().equals(bookName) && !book.isOnLoan()){
                book.checkOut();
                return "Thank you! Enjoy the book";
            }
        }
        return "Sorry, that book is not available";
    }

    String returnBook(String bookName){
        for(Book book: listOfBooks){
            if(book.getTitle().equals(bookName) && book.isOnLoan()){
                book.returnBook();
                return "Thank you for returning the book";
            }
        }
        return "That is not a valid book to return.";
    }

//    public String getMovieInformation() {
//        return "";
//    }
}

