package com.twu.biblioteca;

class Book {
    private String title;
    private String firstName;
    private String lastName;
    private String publishedDate;
    private Boolean onLoan;

    Book(String title, String firstName, String lastName, String publishedDate) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.publishedDate = publishedDate;
        this.onLoan = false;
    }

    String getTitle() {
        return title;
    }

    Boolean getOnLoan() {
        return onLoan;
    }

    String getBookInformation() {
        String columnSeparator = " | ";
        return this.title + columnSeparator + this.lastName + ", " + this.firstName.substring(0,1) + columnSeparator + publishedDate;
    }

    void checkOut() {
        this.onLoan = true;
    }
}
