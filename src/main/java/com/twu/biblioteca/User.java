package com.twu.biblioteca;

public class User {
    private String userId;
    private final String password;

    public User(String userId, String password) {
        checkId(userId);
        this.password = password;
    }

    private void checkId(String userId) {
        if(userId.matches("^([0-9]){4}-([0-9]){4}$")){
            this.userId = userId;
        } else {
            throw new IllegalArgumentException("UserId must be in form XXXX-XXXX where X is a number");
        }
    }

    public Boolean correctPassword(String passwordAttempt){
        return this.password.equals(passwordAttempt);
    }

    public String getUserId() {
        return userId;
    }
}
