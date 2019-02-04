package com.twu.biblioteca;

class Book {
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

    public Integer getPublishedDate() {
        return publishedDate;
    }


    Boolean isOnLoan() {
        return onLoan;
    }


    void checkOut() {
        this.onLoan = true;
    }
    void returnBook() {this.onLoan = false;}
}
