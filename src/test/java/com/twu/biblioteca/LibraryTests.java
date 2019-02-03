package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LibraryTests {

    @Test
    public void DisplayBookInformationShouldReturnCorrectString() {
        Library library = new Library();
        String result = library.getBookInformation();

        String expectedString = String.format("Dark Places | Flynn, G | 2011\nTalent Is Overrated | Colvin, G | 2008\nFactfulness | Rosling, H | 2018\n");
        assertEquals(expectedString, result);
    }

}
