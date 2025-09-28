package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RESP_04_Test {

    @Test
    @DisplayName("Check the notification after login")
    void RESP_04_test_01(){
        Accounts accounts = new InitializeAccounts().initializeAccounts();
        Borrower borrower = accounts.all().get(0);


        Catalogue catalogue = new InitializeLibrary().initializeLibrary();
        Book book = catalogue.getBook(0);

        book.setStatus(Book.Status.Checked_out);
        assertEquals(Book.Status.Checked_out, book.getStatus());

        book.setStatus(Book.Status.Available);
        assertEquals(Book.Status.Available, book.getStatus());

        holdList holds = new holdList();
        holds.placeHold(borrower, book);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Screen screen = new Screen();
        screen.loginNotification(holds.bookAvailable(borrower));

        String output = out.toString();
        assertTrue(output.contains("The books returned and you can borrow right now!"));
        assertTrue(output.contains(book.getTitle()));
    }

}
