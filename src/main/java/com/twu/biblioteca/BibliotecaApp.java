package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String[] args) {
        Library library = new Library();
        Console console = new Console(library);

        System.out.println(console.getWelcomeMessage());
        System.out.println(console.getMenuOptions());
//
//        BufferedReader reader =
//                new BufferedReader(new InputStreamReader(System.in));
//        String input = reader.readLine();
        console.ProcessUserInput(1);
    }

}
