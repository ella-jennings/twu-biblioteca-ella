package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.ILibraryItem;
import com.twu.biblioteca.LibraryItems.Movie;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(Theories.class)
public class UserTests {

    @Test
    public void UserMustBeInitialisedWithIdInFormXXXXXXXX(){
        User user = new User("1234-5678", "password");
        Assert.assertEquals("1234-5678", user.getUserId());
    }

    @Theory
    public void IncorrectUserIdThrowsException(String candidate){
        try {
            new User(candidate, "password");
        } catch (IllegalArgumentException expectedEx){
            Assert.assertEquals("UserId must be in form XXXX-XXXX where X is a number", expectedEx.getMessage());
        }
    }
    public static @DataPoints String[] candidates = {"1", "ab23-5623", "12345-6789", "1234-56789", "1234_5678"};

    @Test
    public void CorrectPasswordReturnsTrue(){
        User user = new User("1234-5678", "password");
        Boolean result = user.isCorrectPassword("password");
        Assert.assertEquals(true, result);
    }
    @Test
    public void CorrectPasswordReturnsFalse(){
        User user = new User("1234-5678", "password");
        Boolean result = user.isCorrectPassword("password123");
        Assert.assertEquals(false, result);
    }

    @Test
    public void LibraryItemsCanBeAddedToUser(){
        Book book1 = new Book(1,"Dark Places", "Gillian", "Flynn", 2011);
        Movie movie2 = new Movie(5, "Die Hard 2", 1992, "Director", 7);
        User user = new User("1234-5678", "password");
        user.addItem(book1);
        user.addItem(movie2);
        List<ILibraryItem> expectedList = Arrays.asList(book1, movie2);
        Assert.assertEquals( expectedList, user.getCheckedOutItems());
    }

    @Test
    public void LibraryItemsCanBeRemovedFromUser(){
        Book book1 = new Book(1,"Dark Places", "Gillian", "Flynn", 2011);
        Movie movie2 = new Movie(5, "Die Hard 2", 1992, "Director", 7);
        User user = new User("1234-5678", "password");
        user.addItem(book1);
        user.addItem(movie2);
        user.removeItem(book1);
        List<ILibraryItem> expectedList = Arrays.asList(movie2);
        Assert.assertEquals( expectedList, user.getCheckedOutItems());
    }
}
