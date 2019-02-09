package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BookTests {
    private String[] testBook;
    private final int YEAR = 2011;

    @Before
    public void setUp() {
        testBook = new String[]{"Dark Places", "Gillian", "Flynn"};
    }

    @Test
    public void CheckoutBookSetsOnLoanAsTrue_ReturnSetsOnLoanAsFalse(){
        Book book = new Book(testBook[0], testBook[1], testBook[2], YEAR);
        assertEquals(false, book.isOnLoan());
        book.checkOutItem();
        assertEquals(true, book.isOnLoan());
        book.returnItem();
        assertEquals(false, book.isOnLoan());
    }
}
