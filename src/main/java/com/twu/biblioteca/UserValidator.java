package com.twu.biblioteca;

public class UserValidator {
    private final ConsoleReader consoleReader;
    private final ConsolePrinter consolePrinter;
    private final Library library;
    private User user;

    public UserValidator(ConsoleReader consoleReader, ConsolePrinter consolePrinter, Library library) {
        this.consoleReader = consoleReader;
        this.consolePrinter = consolePrinter;
        this.library = library;
    }

    public User logInUser() {
        Boolean validUser = checkUserId();
        while(!validUser){
            validUser = checkUserId();
        }
        Boolean login = checkPassword(user);
        while(!login){
            consolePrinter.printLine("Incorrect password");
            login = checkPassword(user);
        }
        return user;
    }

    private Boolean checkPassword(User user) {
        consolePrinter.printLine("Please enter password: ");
        String passwordAttempt = consoleReader.getNextLine();
        return user.isCorrectPassword(passwordAttempt);
    }

    private Boolean checkUserId() {
        consolePrinter.printLine("Please enter User Id: ");
        String userId = consoleReader.getNextLine();
        if(!userId.matches("^([0-9]){4}-([0-9]){4}$")){
            consolePrinter.printLine("User Id must be in format XXXX-XXXX where X is a number");
            return false;
        }
        try {
            user = library.getUsers()
                    .stream()
                    .filter(x -> x.getUserId().equals(userId))
                    .findFirst()
                    .get();
        } catch (Exception ex) {
            consolePrinter.printLine("User not found!");
            return false;
        }
        return true;
    }
}
