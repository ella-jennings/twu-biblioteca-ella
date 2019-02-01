package com.twu.biblioteca;

import java.util.Arrays;
import java.util.List;

public class BibliotecaApp {
    private static List<String> listOfBooks = Arrays.asList("Dark Places", "Talent Is Overrated", "Factfulness");


    public static void main(String[] args) {

        System.out.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
        for (String book: listOfBooks) {
            System.out.println(book);
        }
    }

}
