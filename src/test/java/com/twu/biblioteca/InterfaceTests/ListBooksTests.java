package com.twu.biblioteca.InterfaceTests;

import com.twu.biblioteca.MenuOptions.IMenuOption;
import com.twu.biblioteca.MenuOptions.ListBooks;
import com.twu.biblioteca.Library;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;


public class ListBooksTests {
    @Mock
    Library mockLibrary;

    @Before
    public void SetUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void ExecuteShouldCallLibraryGetBookInformation(){
        IMenuOption booksList = new ListBooks(mockLibrary);
        booksList.executeOption();
        verify(mockLibrary).getBookInformation();
    }
}
