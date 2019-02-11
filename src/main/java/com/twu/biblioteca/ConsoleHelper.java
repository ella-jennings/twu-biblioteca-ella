package com.twu.biblioteca;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConsoleHelper {

    public String getMenu(User user) {
        Map<String, String> menuOptions = new LinkedHashMap<>();
        menuOptions.put("1", "List Of Books");
        menuOptions.put("2", "Checkout a Book");
        menuOptions.put("3", "Return a Book");
        menuOptions.put("4", "List Of Movies");
        menuOptions.put("5", "Checkout a Movie");
        menuOptions.put("6", "Return a Movie");
        if(user != null){
            menuOptions.put("D", "View my details");
            menuOptions.put("L", "Log out");
        } else {
            menuOptions.put("L", "Login");
        }
        menuOptions.put("Q", "Quit");

        StringBuilder options = new StringBuilder();
        for(Map.Entry<String, String> entry: menuOptions.entrySet()){
            options.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }
         return options.toString();
    }
}
