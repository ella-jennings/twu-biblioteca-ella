package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.ILibraryItem;

class Book implements ILibraryItem {
    private String title;
    private String firstName;
    private String lastName;
    private int publishedDate;
    private Boolean onLoan = false;

    Book(String title, String firstName, String lastName, Integer publishedDate) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getDate() {
        return publishedDate;
    }

    public Boolean isOnLoan() {
        return onLoan;
    }


    void checkOut() {
        this.onLoan = true;
    }
    void returnBook() {this.onLoan = false;}
}
