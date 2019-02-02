package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String[] args) {
        Menu menu = new Menu();
        Library library = new Library();

        System.out.println(menu.getWelcomeMessage());
//
//        BufferedReader reader =
//                new BufferedReader(new InputStreamReader(System.in));
//        String input = reader.readLine();

        for (Book book: library.getAllBooks()) {
            System.out.println(book.getBookInformation());
        }
    }

}
