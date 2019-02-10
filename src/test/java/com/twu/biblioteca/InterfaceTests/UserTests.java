package com.twu.biblioteca.InterfaceTests;

import com.twu.biblioteca.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

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
        Boolean result = user.correctPassword("password");
        Assert.assertEquals(true, result);
    }
    @Test
    public void CorrectPasswordReturnsFalse(){
        User user = new User("1234-5678", "password");
        Boolean result = user.correctPassword("password123");
        Assert.assertEquals(false, result);
    }
}
