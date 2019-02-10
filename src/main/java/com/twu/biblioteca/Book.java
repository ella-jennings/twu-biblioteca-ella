package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.ILibraryItem;

class Book extends ItemLoan implements ILibraryItem {
    private Integer id;
    private String title;
    private String firstName;
    private String lastName;
    private int publishedDate;


    Book(Integer id, String title, String firstName, String lastName, Integer publishedDate) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.publishedDate = publishedDate;
    }

    @Override
    public Integer getId() {
        return id;
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

}
