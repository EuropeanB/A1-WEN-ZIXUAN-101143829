package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

// Test the Main menu
// UC-01: 6

public class RESP_05_Test {

    @Test
    @DisplayName("UC-01-6: Check the main menu")
    void RESP_05_test_01() {
        // Input for testing
        String input = "borrower01\n123\n3\n";
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

        // Output
        String output = captured.toString();
        System.out.println("------------ Output -------------");
        System.out.println(output);
        System.out.println("---------------------------------");

        // Test
        assertTrue(output.contains("Login Success!"));
        assertTrue(output.contains("Please select"));
        assertTrue(output.contains("borrower01"));
        //assertTrue(output.toLowerCase().contains("not yet"));
    }
}


