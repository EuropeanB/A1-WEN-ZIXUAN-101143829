package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

// Logout process
// UC-04 1-3

public class RESP_06_Test {

    @Test
    @DisplayName("Check if current user session cleared")
    void RESP_06_test_01(){
        Accounts accounts = new InitializeAccounts().initializeAccounts();
        Authentication auth = new Authentication(accounts);

        Borrower borrower = accounts.all().get(0);
        assertTrue(auth.login(borrower.getUsername(), borrower.getPassword()));
        assertNotNull(auth.getCurrentUser());

        auth.logout();

        assertNull(auth.getCurrentUser());
    }

    @Test
    @DisplayName("Check ")
    void RESP_06_test_02(){
        String input = "borrower01\n123\n3\ny\n";
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

        assertTrue(output.contains("logged out"));

    }
}
