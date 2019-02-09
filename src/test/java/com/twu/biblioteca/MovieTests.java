package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.Movie;
import org.junit.Assert;
import org.junit.Test;

public class MovieTests {
    @Test
    public void MovieWithRatingReturnsRatingOutOf10(){
        Movie movie = new Movie("title", 2011, "director", 5);
        Assert.assertEquals("5/10", movie.getRating());
    }

    @Test
    public void MovieWithNoRatingReturnsUnrated(){
        Movie movie = new Movie("title", 2011, "director");
        Assert.assertEquals("Unrated", movie.getRating());
    }

}
