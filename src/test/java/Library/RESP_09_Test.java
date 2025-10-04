package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

// Check the user sekection process
// UC-02: 3

public class RESP_09_Test {

    @Test
    @DisplayName("UC-02-3: Check the user selection process")
    public void RESP_09_test_01(){
        // Input for testing
        String input = "borrower02\n456\n1\n21\nabc\n";
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

        // Test the selection process
        assertTrue(output.contains("Which book you would like to borrow?"));
        assertTrue(output.contains("Invalid range, please enter a number between 1 and 20!"));
        assertTrue(output.contains("Invalid input, try again!"));
    }
}
