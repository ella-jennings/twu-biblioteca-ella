package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.ILibraryItem;
import com.twu.biblioteca.LibraryItems.Movie;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Library {

    private final List<ILibraryItem> libraryItemList;
    private List<User> users;

    Library(List<ILibraryItem> listOfLibraryItems, List<User> users) {
        this.libraryItemList = listOfLibraryItems;
        this.users = users;
    }


    public String getBookInformation() {
        StringBuilder bookInformation = new StringBuilder();

        for(Book book: this.getCollectionOfTypeTFilteredByLoanStatus(Book.class, false, libraryItemList)) {
            String[] items = getBookProperties(book);
            bookInformation.append(buildLibraryItemInformation(items));
        }
        return bookInformation.toString();
    }


    public String getMovieInformation() {
        StringBuilder movieInformation = new StringBuilder();

        for(Movie movie: this.getCollectionOfTypeTFilteredByLoanStatus(Movie.class, false, libraryItemList)){
            String[] items = getMovieProperties(movie);
            movieInformation.append(buildLibraryItemInformation(items));
        }
        return movieInformation.toString();
    }

    <T extends ILibraryItem> String checkOut(Class<T> type, String identifier, User user) {
        ILibraryItem item = locateItem(type, identifier, false, libraryItemList);
        String typeName = type.getSimpleName().toLowerCase();
        if(item == null){
            return "Sorry, that " + typeName + " is not available";
        }
        else {
            item.checkOutItem();
            user.addItem(item);
            return "Thank you! Enjoy the " + typeName;
        }
    }

    String checkOutMovie(String movieIdentifier, User user) {
        Movie item = locateItem(Movie.class, movieIdentifier, false, libraryItemList);
        if(item == null){
            return "Sorry, that movie is not available";
        }
        else {
            item.checkOutItem();
            user.addItem(item);
            return "Thank you! Enjoy the movie";
        }
    }

    String returnBook(String bookIdentifier, User user){
        Book itemInLibrary = locateItem(Book.class, bookIdentifier, true, libraryItemList);
        Book itemInUserCheckOuts = locateItem(Book.class, bookIdentifier, true, user.getCheckedOutItems());
        if(itemInLibrary == null || itemInUserCheckOuts == null){
            return "That is not a valid book to return.";
        }
        else {
            itemInLibrary.returnItem();
            user.removeItem(itemInLibrary);
            return "Thank you for returning the book";
        }
    }

     String returnMovie(String movieIdentifier, User user){
        Movie item = locateItem(Movie.class, movieIdentifier, true, libraryItemList);
        Movie itemInUserCheckOuts = locateItem(Movie.class, movieIdentifier, true, user.getCheckedOutItems());
        if(item == null || itemInUserCheckOuts == null){
            return "That is not a valid movie to return.";
        }
        else {
            item.returnItem();
            user.removeItem(item);
            return "Thank you for returning the movie";
        }
    }


    private <T extends ILibraryItem> T locateItem(Class<T> type, String input, Boolean onLoan, List<ILibraryItem> list) {
        Predicate<ILibraryItem> idMatch = x -> x.getId().toString().equals(input);
        Predicate<ILibraryItem> titleMatch = x -> x.getTitle().equals(input);

        return this.getCollectionOfTypeTFilteredByLoanStatus(type, onLoan, list)
                .stream()
                .filter(idMatch.or(titleMatch))
                .findFirst()
                .orElse(null);
    }

    private <T extends ILibraryItem> List<T> getCollectionOfTypeTFilteredByLoanStatus(Class<T> t, Boolean onLoan, List<ILibraryItem> list) {
        return list.stream()
                .filter(t::isInstance)
                .filter(x -> x.isOnLoan().equals(onLoan))
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

    public List<User> getUsers() {
        return users;
    }

    public String getUserInformation(User user) {
        if(!getUsers().contains(user)){
            return "User is not a member of this library";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Details for user ");
            stringBuilder.append(user.getUserId());
            stringBuilder.append("\n");
            stringBuilder.append("Name: ");
            stringBuilder.append(user.getName());
            stringBuilder.append("\n");
            stringBuilder.append("Email: ");
            stringBuilder.append(user.getEmail());
            stringBuilder.append("\n");
            stringBuilder.append("Phone: ");
            stringBuilder.append(user.getPhone());
            stringBuilder.append("\n\n");
            stringBuilder.append("Items on Loan:");
            stringBuilder.append("\n");
            List<ILibraryItem> items = user.getCheckedOutItems();
            for(ILibraryItem item: items) {
                if(item instanceof Book){
                    String[] properties = getBookProperties((Book)item);
                    stringBuilder.append(buildLibraryItemInformation(properties));
                }
                if(item instanceof Movie){
                    String[] properties = getMovieProperties((Movie) item);
                    stringBuilder.append(buildLibraryItemInformation(properties));
                }
            }
            return stringBuilder.toString();
        }
    }

    private String[] getBookProperties(Book book) {
        String authorName = book.getLastName() + ", " + book.getFirstName().substring(0,1);
        return new String[]{book.getId().toString(), book.getTitle(), authorName, book.getDate().toString()};
    }

    private String[] getMovieProperties(Movie movie) {
        return new String[]{movie.getId().toString(), movie.getTitle(), movie.getDate().toString(), movie.getDirector(), movie.getRating()};
    }
}

