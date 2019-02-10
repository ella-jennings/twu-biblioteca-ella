package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovieTests {

    @Test
    public void MovieWithRatingReturnsRatingOutOf10(){
        Movie movie = new Movie(1, "title", 2011, "director", 5);
        Assert.assertEquals("5/10", movie.getRating());
    }

    @Test
    public void MovieWithNoRatingReturnsUnrated(){
        Movie movie = new Movie(2, "title", 2011, "director");
        Assert.assertEquals("Unrated", movie.getRating());
    }

    @Test
    public void CheckoutBookSetsOnLoanAsTrue_ReturnSetsOnLoanAsFalse(){
        Movie movie = new Movie(3, "title", 2011, "director", 6);
        assertEquals(false, movie.isOnLoan());
        movie.checkOutItem();
        assertEquals(true, movie.isOnLoan());
        movie.returnItem();
        assertEquals(false, movie.isOnLoan());
    }

}
