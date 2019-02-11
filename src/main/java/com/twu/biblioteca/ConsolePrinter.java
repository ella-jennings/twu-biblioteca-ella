package com.twu.biblioteca;

import java.io.PrintStream;

public class ConsolePrinter {
    private PrintStream out;

    public ConsolePrinter(PrintStream out) {
        this.out = out;
    }

    public void printLine(String string) {
        out.println(string);
    }

    void print(String string) {
        out.print(string);
    }
}
