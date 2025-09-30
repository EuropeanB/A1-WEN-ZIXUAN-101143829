package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

// Check the system checking during borrowing
// UC-02: 5-7

public class RESP_11_Test {
    private Catalogue catalogue;
    private Accounts accounts;
    private holdList holds;
    private Book book01;
    private Borrower borrower01;
    private Borrower borrower02;

    @BeforeEach
    void setUp() {
        catalogue = new InitializeLibrary().initializeLibrary();
        accounts = new InitializeAccounts().initializeAccounts();
        holds = new holdList();

        book01 = catalogue.getBook(0);
        borrower01 = accounts.all().get(0);
        borrower02 = accounts.all().get(1);

    }

    @Test
    @DisplayName("Check if book is unavailable")
    public void RESP_11_test_01(){
        book01.setStatus(Book.Status.Checked_out);
        assertEquals(Book.Status.Checked_out, book01.getStatus());
    }

    @Test
    @DisplayName("Check if book is on hold")
    public void RESP_11_test_02(){
        book01.setStatus(Book.Status.Available);
        holds.placeHold(borrower02,book01);
        assertTrue(holds.isOnHold(book01, borrower01));
    }

    @Test
    @DisplayName("Check if book is on hold")
    public void RESP_11_test_03(){
        borrower01.increaseBorrowedCount();
        borrower01.increaseBorrowedCount();
        borrower01.increaseBorrowedCount();
        assertTrue(borrower01.getBorrowedCount() == 3);
    }

}
