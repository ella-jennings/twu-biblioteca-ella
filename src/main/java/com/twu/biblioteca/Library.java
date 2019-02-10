package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.ILibraryItem;
import com.twu.biblioteca.LibraryItems.Movie;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class Library {

    private final List<ILibraryItem> libraryItemList;

    Library(List<ILibraryItem> listOfLibraryItems) {
        this.libraryItemList = listOfLibraryItems;
    }


    private <T> List<T> getCollectionOfType(Class<T> t) {
        return libraryItemList.stream()
                .filter(t::isInstance)
                .filter(x -> !x.isOnLoan())
                .map(t::cast)
                .collect(Collectors.toList());
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

    public String getBookInformation() {
        StringBuilder bookInformation = new StringBuilder();

        for(Book book: getCollectionOfType(Book.class)) {
                String authorName = book.getLastName() + ", " + book.getFirstName().substring(0,1);
                String[] items = {book.getTitle(), authorName, book.getDate().toString()};
                bookInformation.append(buildLibraryItemInformation(items));
        }
        return bookInformation.toString();
    }


    public String getMovieInformation() {
        StringBuilder movieInformation = new StringBuilder();

        for(Movie movie: getCollectionOfType(Movie.class)){
            String[] items = {movie.getTitle(), movie.getDate().toString(), movie.getDirector(), movie.getRating()};
            movieInformation.append(buildLibraryItemInformation(items));
        }
        return movieInformation.toString();
    }

    String checkOut(String itemName) {
            for(ILibraryItem item: libraryItemList){
                if(item.getTitle().equals(itemName) && !item.isOnLoan()){
                    item.checkOutItem();
                    return "Thank you! Enjoy the book";
                }
            }
        return "Sorry, that book is not available";
    }

    String returnBook(String bookName){
        for(ILibraryItem book: libraryItemList){
            if(book.getTitle().equals(bookName) && book.isOnLoan()){
                book.returnItem();
                return "Thank you for returning the book";
            }
        }
        return "That is not a valid book to return.";
    }

    public String checkOutMovie(String movieName) {
        return "";
    }
}

