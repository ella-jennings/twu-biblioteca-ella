package com.twu.biblioteca.LibraryItems;

public interface ILibraryItem {
    Integer getId();
    String getTitle();
    Integer getDate();
    Boolean isOnLoan();
    void checkOutItem();
    void returnItem();
}
