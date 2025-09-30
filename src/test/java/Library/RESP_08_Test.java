package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import java.time.LocalDate;

//  Check the books and information
//UC-02: 2.2 - 2.3

public class RESP_08_Test {
    @Test
    @DisplayName("Check the collection display and single status")
    void RESP_08_test_01() {
        String input = "borrower03\n789\n1\n";
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(captured));

        try {
            try {
                new Control().launch();
            } catch (NoSuchElementException eof) {
                // Do nothing
            }
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String output = captured.toString();
        System.out.println("------------ Output -------------");
        System.out.println(output);
        System.out.println("---------------------------------");

        assertTrue(output.contains("Library Collection"));
    }

    @Test
    @DisplayName("Check the various statuses and due date")
    public void RESP_08_test_02(){
        Catalogue catalogue = new InitializeLibrary().initializeLibrary();
        Accounts accounts = new InitializeAccounts().initializeAccounts();
        holdList holds = new holdList();

        Book book01 = catalogue.getBook(0);
        book01.setStatus(Book.Status.Checked_out);
        LocalDate due = LocalDate.now().plusDays(14);
        book01.setDueDate(due);

        Book book02 = catalogue.getBook(1);
        book02.setStatus(Book.Status.On_hold);

        holds.placeHold(accounts.all().get(1),book02);
        Borrower borrower01 = accounts.all().get(0);

        PrintStream out0 = System.out;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buf));

        try {
            Screen screen = new Screen(new ByteArrayInputStream(new byte[0]));
            screen.showCatalogue(catalogue,borrower01, holds);

        } finally {
            System.setOut(out0);
        }

        String out = buf.toString();

        assertTrue(out.contains("Checked Out"));
        assertTrue(out.contains("Due:"));
        assertTrue(out.contains(due.toString()));
        assertTrue(out.contains("On Hold"));

    }
}
