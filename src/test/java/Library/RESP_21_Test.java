package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

// Return book and reservation status becomes On_hold
// UC-03: 3.1/2/3/4/5

public class RESP_21_Test {

    @Test
    @DisplayName("UC-03-3:Return book and reservation status becomes On_hold")
    public void RESP_21_test_01() {

        // Initialization
        Borrower borrower1 = new Borrower("borrower01", "123");
        Borrower borrower2 = new Borrower("borrower02", "456");
        Book book = new Book("Book01", "Author01");

        // Checking before testing
        LocalDate dueDate = LocalDate.now().plusDays(14);
        book.setStatus(Book.Status.Checked_out);
        book.setDueDate(dueDate);
        borrower1.increaseBorrowedCount();
        borrower1.addRecord(new Recording(book, dueDate));

        holdList holds = new holdList();
        holds.placeHold(borrower2, book);

        if (holds.checkReservation(book)) {
            book.setStatus(Book.Status.On_hold);
            book.setDueDate(null);
            borrower1.removeRecord(book);
        } else {
            book.setStatus(Book.Status.Available);
            book.setDueDate(null);
            borrower1.removeRecord(book);
        }

        // On hold checking
        assertEquals(Book.Status.On_hold, book.getStatus());
        assertTrue(book.getDueDate().isEmpty());
        assertTrue(borrower1.getRecords().isEmpty());
        assertEquals(0, borrower1.getBorrowedCount());
    }
}