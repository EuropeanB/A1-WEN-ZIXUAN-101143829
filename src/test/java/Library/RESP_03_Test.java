package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

//  User Authentication and credentials validation
//  UC-01: 1,2,3,4

public class RESP_03_Test {

    @Test
    @DisplayName("UC-01-1/2: Check the login system")
    void RESP_03_test_01(){

        // Initialization
        Accounts accounts = new InitializeAccounts().initializeAccounts();
        Authentication authentication = new Authentication(accounts);

        boolean result01 = authentication.login("borrower01", "123");
        assertTrue(result01);

        boolean result02 = authentication.login("borrower01","1234");
        assertFalse(result02);

        boolean result03 = authentication.login("borrower04","123");
        assertFalse(result03);
    }

    @Test
    @DisplayName("UC-01-3/4: Check the login process")
    void RESP_03_test_02(){
        // Input for testing
        String input = "borrower01\n123\n\n";
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

        assertTrue(output.contains("Login Success!"));
        assertTrue(output.contains("Please select"));
        assertTrue(output.contains("borrower01"));


    }

}
