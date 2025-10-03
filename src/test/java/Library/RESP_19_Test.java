package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

// Check the return book selection amd confirmation
// UC-03: 2,5,6,7

public class RESP_19_Test {

    @Test
    @DisplayName("UC-03-2/5: Check the return book selection")
    public void RESP_19_test_01() {
        // Initialization
        Borrower borrower = new Borrower("borrower1", "123");
        Book book1 = new Book("Book01", "Author01");
        Book book2 = new Book("Book02", "Author02");

        borrower.addRecord(new Recording(book1, LocalDate.now().plusDays(14)));
        borrower.addRecord(new Recording(book2, LocalDate.now().plusDays(14)));

        // Input for testing
        String input = "2\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Screen screen = new Screen(in);

        int selectedIndex = screen.selectBorrowedBook(borrower);

        // Test the selection
        assertEquals(1, selectedIndex);
    }

    @Test
    @DisplayName("UC-03-6/7: Confirm returning a book")
    public void RESP_19_test_02() {
        // initialization
        Book book = new Book("Book01", "Author01");

        // Input for testing
        String input = "y\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Screen screen = new Screen(in);

        boolean confirm = screen.confirmReturn(book);

        // Check the confirmation
        assertTrue(confirm);
    }

}
