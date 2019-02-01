package com.twu.biblioteca;

public class BibliotecaApp {
    static String[] book1 = {"Dark Places", "Gillian", "Flynn", "2011"};
    static String[] book2 = {"Talent Is Overrated", "Geoff", "Colvin", "2008"};
    static String[] book3 = {"Factfulness", "Hans", "Rosling", "2018"};


    public static void main(String[] args) {
        Menu menu = new Menu();
        String[][] listOfBooks = {book1, book2, book3};

        System.out.println(menu.getWelcomeMessage());
//
//        BufferedReader reader =
//                new BufferedReader(new InputStreamReader(System.in));
//        String input = reader.readLine();

        for (String[] book: listOfBooks) {
            Book newBook = new Book(book[0], book[1], book[2], book[3]);
            System.out.println(newBook.getBookInformation());
        }
    }

}
