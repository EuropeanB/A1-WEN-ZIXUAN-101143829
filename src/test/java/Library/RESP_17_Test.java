package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

// Check the failed conditions when reserve a book
// UC-02: 6.2/3

public class RESP_17_Test {

    @Test
    @DisplayName("UC-02-6a.3: Check the book booking failed because borrower already borrowed this book")
    public void RESP_17_test_01() {

        // Initialization
        Borrower borrower = new Borrower("borrower02", "456");
        Book book = new Book("Book20", "Author20");
        LocalDate dueDate = LocalDate.now().plusDays(14);

        book.setStatus(Book.Status.Checked_out);
        book.setDueDate(dueDate);
        borrower.increaseBorrowedCount();
        borrower.addRecord(new Recording(book, dueDate));

        boolean alreadyBorrowed = borrower.getRecords().stream()
                .anyMatch(r -> r.getBook().equals(book));

        // Test the book borrowed by himself condition
        assertTrue(alreadyBorrowed);
    }

    @Test
    @DisplayName("UC-02-6a.2Check the book booking failed because borrower already reserved this book")
    public void RESP_17_test_02() {

        // Initialization
        Borrower borrower02 = new Borrower("borrower02", "456");
        Book book = new Book("Book20", "Author20");

        holdList holds = new holdList();

        holds.placeHold(borrower02, book);

        boolean hasReserved = holds.hasReserved(book,borrower02);

        // Test the book already reserved by himself condition
        assertTrue(hasReserved);
    }
}
