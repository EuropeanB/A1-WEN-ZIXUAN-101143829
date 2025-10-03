package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

//

public class RESP_20_Test {

    @Test
    @DisplayName("Check the updating on recording and book status after book returned")
    public void RESP_20_test_01() {
        Borrower borrower = new Borrower("borrower01", "123");
        Book book = new Book("Book01", "Author01");
        LocalDate dueDate = LocalDate.now().plusDays(14);

        book.setStatus(Book.Status.Checked_out);
        book.setDueDate(dueDate);
        borrower.increaseBorrowedCount();
        borrower.addRecord(new Recording(book, dueDate));

        assertEquals(Book.Status.Checked_out, book.getStatus());
        assertEquals(1, borrower.getBorrowedCount());
        assertFalse(borrower.getRecords().isEmpty());

        book.setStatus(Book.Status.Available);
        book.setDueDate(null);
        borrower.removeRecord(book);

        assertEquals(Book.Status.Available, book.getStatus());
        assertTrue(book.getDueDate().isEmpty());
        assertEquals(0, borrower.getBorrowedCount());
        assertTrue(borrower.getRecords().isEmpty());
    }
}
