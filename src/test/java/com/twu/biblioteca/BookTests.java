package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BookTests {

    @Test
    public void CheckoutBookSetsOnLoanAsTrue_ReturnSetsOnLoanAsFalse(){
        Book book = new Book(1, "Dark Places", "Gillian", "Flynn", 2011);
        assertEquals(false, book.isOnLoan());
        book.checkOutItem();
        assertEquals(true, book.isOnLoan());
        book.returnItem();
        assertEquals(false, book.isOnLoan());
    }
}
