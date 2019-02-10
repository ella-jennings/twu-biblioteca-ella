package com.twu.biblioteca.LibraryItems;

public interface ILibraryItem {
    String getTitle();
    Integer getDate();
    Boolean isOnLoan();
    void checkOutItem();
    void returnItem();
}
