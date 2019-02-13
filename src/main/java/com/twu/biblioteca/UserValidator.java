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
        this.user = null;
    }

    public void logInUser() {
        Boolean validUser = checkUserId();
        int userAttempt = 0;
        while(!validUser && userAttempt < 4){
            userAttempt += 1;
            validUser = checkUserId();
        }
        if( userAttempt == 4 ) {
            consolePrinter.printLine("User attempts max reached");
            return;
        }

        Boolean login = checkPassword();
        int passwordAttempt = 0;
        while(!login && passwordAttempt < 4){
            consolePrinter.printLine("Incorrect password");
            passwordAttempt += 1;
            login = checkPassword();
        }
        if( passwordAttempt == 4 ) {
            consolePrinter.printLine("Password attempts max reached");
            user = null;
        }
    }

    private Boolean checkPassword() {
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
            user = null;
            consolePrinter.printLine("User not found!");
            return false;
        }
        return true;
    }


    public User getCurrentUser() {
        return user;
    }

    public Boolean userIsLoggedIn() {
        return user != null;
    }

    public void logOutUser() {
        user = null;
    }
}
