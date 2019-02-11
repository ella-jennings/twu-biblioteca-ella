package com.twu.biblioteca.InterfaceTests;

import com.twu.biblioteca.Book;
import com.twu.biblioteca.LibraryItems.Movie;
import com.twu.biblioteca.MenuOptions.IMenuOption;
import com.twu.biblioteca.MenuOptions.ListItems;
import com.twu.biblioteca.Library;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;


public class ListItemsTests {
    @Mock
    Library mockLibrary;

    @Before
    public void SetUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void ExecuteShouldCallLibraryGetBookInformation(){
        IMenuOption booksList = new ListItems(mockLibrary, Book.class);
        booksList.executeOption();
        verify(mockLibrary).getInformation(Book.class);
    }

    @Test
    public void ExecuteShouldCallLibraryGetMovieInformation(){
        IMenuOption booksList = new ListItems(mockLibrary, Movie.class);
        booksList.executeOption();
        verify(mockLibrary).getInformation(Movie.class);
    }
}
