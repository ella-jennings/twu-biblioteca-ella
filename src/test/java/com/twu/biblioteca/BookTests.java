package com.twu.biblioteca;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BookTests {
    String[] testBook = {"Dark Places", "Gillian", "Flynn", "2011"};

    @Test
    public void BooksShouldContainTitleAuthorAndDateInformation() {
        Book book = new Book(testBook[0], testBook[1], testBook[2], testBook[3]);
        String result = book.getBookInformation();
        assertThat(result, is(equalTo("Dark Places | Flynn, G | 2011")));
    }

    @Test
    public void CheckoutBookSetsOnLoanAsTrue_ReturnSetsOnLoanAsFalse(){
        Book book = new Book(testBook[0], testBook[1], testBook[2], testBook[3]);
        assertEquals(false, book.isOnLoan());
        book.checkOut();
        assertEquals(true, book.isOnLoan());
        book.returnBook();
        assertEquals(false, book.isOnLoan());
    }
}
