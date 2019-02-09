package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;

import java.util.List;

public class Library {

    private final List<Book> listOfBooks;
    private final List<Movie> listOfMovies;

    Library(List<Book> listOfBooks, List<Movie> listOfMovies) {
        this.listOfBooks = listOfBooks;
        this.listOfMovies = listOfMovies;
    }


    public String getBookInformation() {
        StringBuilder bookInformation = new StringBuilder();
        for(Book book: listOfBooks) {
            if(!book.isOnLoan()){
                String authorName = book.getLastName() + ", " + book.getFirstName().substring(0,1);
                String[] items = {book.getTitle(), authorName, book.getDate().toString()};
                bookInformation.append(buildLibraryItemInformation(items));
            }
        }
        return bookInformation.toString();
    }

    public String getMovieInformation() {
        StringBuilder movieInformation = new StringBuilder();
        for(Movie movie: listOfMovies){
            String[] items = {movie.getTitle(), movie.getDate().toString(), movie.getDirector(), movie.getRating()};
            movieInformation.append(buildLibraryItemInformation(items));
        }
        return movieInformation.toString();
    }

    private String buildLibraryItemInformation(String[] items) {
        String columnSeparator = " | ";
        int indexOfLastItem = items.length -1;
        StringBuilder stringToReturn = new StringBuilder();
        for(int i = 0; i < items.length; i++){
            if(i == indexOfLastItem){
                stringToReturn.append(items[i]);
                stringToReturn.append("\n");
            }
            else{
                stringToReturn.append(items[i]);
                stringToReturn.append(columnSeparator);
            }
        }
        return stringToReturn.toString();
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
}

