package com.twu.biblioteca.MenuOptions;

import com.twu.biblioteca.ConsoleTerminator;

public class Quit implements IMenuOption {
    private ConsoleTerminator consoleTerminator;

    public Quit(ConsoleTerminator consoleTerminator) {
        this.consoleTerminator = consoleTerminator;
    }

    public void executeOption() {
        consoleTerminator.exitApplication();
    }
}
