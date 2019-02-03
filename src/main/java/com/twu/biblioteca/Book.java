package com.twu.biblioteca;

public class Book {
    private String title;
    private String firstName;
    private String lastName;
    private String publishedDate;

    public Book(String title, String firstName, String lastName, String publishedDate) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.publishedDate = publishedDate;

    }

    String getBookInformation() {
        String columnSeparator = " | ";
        return this.title + columnSeparator + this.lastName + ", " + this.firstName.substring(0,1) + columnSeparator + publishedDate;
    }
}
