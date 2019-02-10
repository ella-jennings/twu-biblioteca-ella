package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.ILibraryItem;
import com.twu.biblioteca.LibraryItems.Movie;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Library {

    private final List<ILibraryItem> libraryItemList;

    Library(List<ILibraryItem> listOfLibraryItems) {
        this.libraryItemList = listOfLibraryItems;
    }


    private <T> List<T> getCollectionOfTypeTFilteredByLoanStatus(Class<T> t, Boolean onLoan) {
        return libraryItemList.stream()
                .filter(t::isInstance)
                .filter(x -> x.isOnLoan().equals(onLoan))
                .map(t::cast)
                .collect(Collectors.toList());
    }

    public String getBookInformation() {
        StringBuilder bookInformation = new StringBuilder();

        for(Book book: getCollectionOfTypeTFilteredByLoanStatus(Book.class, false)) {
                String authorName = book.getLastName() + ", " + book.getFirstName().substring(0,1);
                String[] items = {book.getId().toString(), book.getTitle(), authorName, book.getDate().toString()};
                bookInformation.append(buildLibraryItemInformation(items));
        }
        return bookInformation.toString();
    }


    public String getMovieInformation() {
        StringBuilder movieInformation = new StringBuilder();

        for(Movie movie: getCollectionOfTypeTFilteredByLoanStatus(Movie.class, false)){
            String[] items = {movie.getId().toString(), movie.getTitle(), movie.getDate().toString(), movie.getDirector(), movie.getRating()};
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

    String checkOutBook(String booIdentifier) {
        Book item = locateItem(Book.class, booIdentifier, false);
        if(item == null){
            return "Sorry, that book is not available";
        }
        else {
            item.checkOutItem();
            return "Thank you! Enjoy the book";
        }
    }

    public String checkOutMovie(String movieIdentifier) {
        Movie item = locateItem(Movie.class, movieIdentifier, false);
        if(item == null){
            return "Sorry, that movie is not available";
        }
        else {
            item.checkOutItem();
            return "Thank you! Enjoy the movie";
        }
    }

    String returnBook(String bookIdentifier){
        Book item = locateItem(Book.class, bookIdentifier, true);
        if(item == null){
            return "That is not a valid book to return.";
        }
        else {
            item.returnItem();
            return "Thank you for returning the book";
        }
    }


    private <T extends ILibraryItem> T locateItem(Class<T> type, String input, Boolean onLoan) {
        Predicate<ILibraryItem> idMatch = x -> x.getId().toString().equals(input);
        Predicate<ILibraryItem> titleMatch = x -> x.getTitle().equals(input);

        return getCollectionOfTypeTFilteredByLoanStatus(type, onLoan)
                .stream()
                .filter(idMatch.or(titleMatch))
                .findFirst()
                .orElse(null);
    }
}

