package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

// A-TEST-02
// Initialization and Authentication with Error Handling
// Path: UC-01 - 4 - 1 - 1 - 3
// Test: borrower01 login, logout, borrower99 login(failed), borrower02 login, borrower02 logout

public class Acceptance_02_Test {
    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;

        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("A-TEST-02")
    public void A_TEST_02() {
        // Input for testing
        String input =  "borrower01\n123\n3\ny\n" +   // UC-01/04
                        "borrower99\n000\n" +          // UC-01
                        "borrower02\n456\n3\ny\n";     // UC-01/04

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            new Control().launch();
        } catch (Exception ignored) {
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        // Output
        String output = capturedOut.toString();
        System.out.println("------------ Output -------------");
        System.out.println(output);
        System.out.println("---------------------------------");

        // --- Tests ---

        // UC-01: borrower01 login
        assertTrue(output.contains("Username:"));
        assertTrue(output.contains("password:"));
        assertTrue(output.contains("Login Success!"));
        assertTrue(output.contains("Welcome! borrower01"));

        // UC-04: borrower01 logout
        assertTrue(output.contains("Are you sure you want to logout? (y/n): "));
        assertTrue(output.contains("logged out"));

        // UC-01: borrower99 login
        assertTrue(output.contains("Login failed! Please try again."));

        // UC-01: borrower02 login
        assertTrue(output.contains("Welcome! borrower02"));

        // UC-04: borrower02 logout
        assertTrue(output.contains("logged out"));
    }
}