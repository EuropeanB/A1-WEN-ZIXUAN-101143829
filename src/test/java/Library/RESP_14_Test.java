package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

// Check the system recording
// UC-02: 9

public class RESP_14_Test {

    @Test
    @DisplayName("UC-02-9: Check the system recording")
    public void RESP_14_test_01(){
        // Initialization
        Borrower borrower = new Borrower("borrower01", "123");
        Book book = new Book("Book01", "Author01");
        LocalDate due = LocalDate.now().plusDays(14);

        Recording record = new Recording(book, due);
        borrower.addRecord(record);

        // Test the system recording
        assertEquals(1, borrower.getRecords().size());   // 确认记录数增加
        assertEquals(book, borrower.getRecords().get(0).getBook());
        assertEquals(due, borrower.getRecords().get(0).getDueDate());

    }
}
