package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

// Check the date calculation
// UC-02: 8

public class RESP_12_Test {

    @Test
    @DisplayName("UC-02-8: Check the date calculation")
    void RESP_12_test_01(){
        // Initialization
        Catalogue catalogue = new InitializeLibrary().initializeLibrary();
        Accounts accounts = new InitializeAccounts().initializeAccounts();
        Authentication auth = new Authentication(accounts);

        Borrower borrower01 = accounts.all().get(0);
        Book book01 = catalogue.getBook(0);

        LocalDate today = LocalDate.now();
        LocalDate expectedDue = today.plusDays(14);

        book01.setStatus(Book.Status.Checked_out);
        book01.setDueDate(expectedDue);
        borrower01.increaseBorrowedCount();

        // Check if due date setting is correct
        assertEquals(Book.Status.Checked_out, book01.getStatus());
        assertTrue(book01.getDueDate().isPresent());
        assertEquals(today.plusDays(14), book01.getDueDate().get());
    }
}
