package com.twu.biblioteca;

import org.junit.Assert;
import org.junit.Test;


public class MenuTests {
    @Test
    public void GetWelcomeShouldReturnWelcomeMessage(){
        Menu menu = new Menu();
        String result = menu.getWelcomeMessage();

        Assert.assertEquals("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!", result);
    }
    @Test
    public void GetMenuOptionsShouldReturnAvailableOptions() {
        Menu menu = new Menu();
        String[] result = menu.getMenuOptions();

        Assert.assertEquals("1 - List Of Books", result[0]);
    }
}
