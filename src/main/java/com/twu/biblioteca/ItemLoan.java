package com.twu.biblioteca;

public class ItemLoan {
    private Boolean onLoan = false;
    public Boolean isOnLoan() {
        return onLoan;
    }


   public void checkOutItem() {
        this.onLoan = true;
    }
    public void returnItem() {this.onLoan = false;}
}
