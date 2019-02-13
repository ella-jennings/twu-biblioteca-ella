package com.twu.biblioteca.MenuOptions;

import com.twu.biblioteca.UserValidator;

public class Login implements IMenuOption {
    private UserValidator userValidator;

    public Login(UserValidator userValidator) {

        this.userValidator = userValidator;
    }

    public void executeOption() {
        if(userValidator.userIsLoggedIn()){
            userValidator.logOutUser();
        } else {
            userValidator.logInUser();
        }
    }
}
