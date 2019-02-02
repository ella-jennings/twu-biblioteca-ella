package com.twu.biblioteca;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LibraryTests {
    @Test
    public void GetBooksShouldReturnListOfBooks(){
        Library library = new Library();
        List<Book> result = library.getAllBooks();

        Assert.assertEquals(3, result.size());
    }

}
