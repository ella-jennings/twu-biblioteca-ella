package com.twu.biblioteca;

import com.twu.biblioteca.LibraryItems.ILibraryItem;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private final String name;
    private final String email;
    private final String phone;
    private final String password;
    private List<ILibraryItem> checkedOutItems = new ArrayList<>();

    public User(String userId, String password, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public Boolean isCorrectPassword(String passwordAttempt){
        return this.password.equals(passwordAttempt);
    }

    public String getUserId() {
        return userId;
    }

    public void addItem(ILibraryItem item) {
        checkedOutItems.add(item);
    }

    public void removeItem(ILibraryItem item) {
        if(checkedOutItems.contains(item)){
            checkedOutItems.remove(item);
        }
    }

    public List<ILibraryItem> getCheckedOutItems(){
        return checkedOutItems;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
