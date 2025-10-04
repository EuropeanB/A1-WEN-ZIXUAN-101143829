package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

// Check the return book menu and failed condition
// UC-03: 1/1.1

public class RESP_18_Test {

    @Test
    @DisplayName("UC-03-1.1: Check if there's no borrowed book when user want to return book")
    public void RESP_18_test_01() {
        // Output
        ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));

        // Initialization
        Borrower borrower = new Borrower("borrower01", "123");
        Screen screen = new Screen(System.in);

        screen.showBorrowedBooks(borrower);

        // Test the no borrowed book condition
        String output = capturedOut.toString();
        assertTrue(output.contains("You have no borrowed books!"));
    }

    @Test
    @DisplayName("UC-03-1: Check the return book menu")
    public void RESP_18_test_02() {
        //  Output
        ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));

        // Initialization
        Borrower borrower = new Borrower("borrower01", "123");
        Book book = new Book("Book01", "Author01");
        LocalDate dueDate = LocalDate.now().plusDays(7);
        borrower.addRecord(new Recording(book, dueDate));

        Screen screen = new Screen(System.in);

        screen.showBorrowedBooks(borrower);

        // Test the return book menu Display
        String output = capturedOut.toString();
        assertTrue(output.contains("Borrowed Books"));
        assertTrue(output.contains("Book01"));
        assertTrue(output.contains("Author01"));
        assertTrue(output.contains(dueDate.toString()));
    }
}