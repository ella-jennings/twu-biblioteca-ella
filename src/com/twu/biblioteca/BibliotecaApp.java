package com.twu.biblioteca;

import java.util.List;

public class BibliotecaApp {

    public static void main(String[] args) {
        Menu menu = new Menu();
        Library library = new Library();

        System.out.println(menu.getWelcomeMessage());
        System.out.println(menu.getMenuOptions());
//
//        BufferedReader reader =
//                new BufferedReader(new InputStreamReader(System.in));
//        String input = reader.readLine();
        System.out.print(library.displayBookInformation());
    }

}
