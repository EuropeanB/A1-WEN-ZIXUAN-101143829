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

// A-TEST-01
// Multi-User Borrow and Return with Availability Validated
// Path: UC-01 - 2 - 4 - 1 - 2 - 4 - 1 - 3 - 4 - 1 - 2 - 4
// Test: user 1 login, borrow, logout, user2 login, see book checked out
//       user 2 logout, user 1 login, return, user 1 logout, user 2 login, borrow, user 2 logout

public class Acceptance_01_Test {
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
    @DisplayName("A-TEST-01")
    public void A_TEST_01() {
        // Input for testing
        String input =  "borrower01\n123\n1\n" + // UC-01
                        "1\ny\ny\n" +  // UC-02
                        "3\ny\n" + // UC-04
                        "borrower02\n456\n1\n" + // UC-01
                        "1\nn\n" + // UC-02
                        "3\ny\n" + // UC-04
                        "borrower01\n123\n2\n" + // UC-01
                        "1\ny\n" + // UC-03
                        "3\ny\n" + // UC-04
                        "borrower02\n456\n1\n" + // UC-01
                        "1\ny\ny\n" +  // UC-02
                        "3\ny\n"; // UC-04


        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            new Control().launch();
        } catch (Exception ignored) {

        } finally{
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        // Output
        String output = capturedOut.toString();
        System.out.println("------------ Output -------------");
        System.out.println(output);
        System.out.println("---------------------------------");

        //Test
        // UC-01: borrower01 login
        assertTrue(output.contains("Username:"));
        assertTrue(output.contains("password:"));
        assertTrue(output.contains("Login Success!"));
        assertTrue(output.contains("Welcome! borrower01"));
        assertTrue(output.contains("There is no any notification..."));

        // UC-02: borrow
        assertTrue(output.contains("Library Collection"));
        assertTrue(output.contains("book01 — author01  [Available]"));
        assertTrue(output.contains("You borrowed this book!"));

        // UC-04: logout
        assertTrue(output.contains("Are you sure you want to logout? (y/n): "));
        assertTrue(output.contains("logged out"));

        // UC-01: borrower02 login
        assertTrue(output.contains("Welcome! borrower02"));
        assertTrue(output.contains("There is no any notification..."));

        // UC-02: see the book checked out
        assertTrue(output.contains("book01 — author01  [Checked Out]"));
        assertTrue(output.contains("borrowing cancelled"));

        // UC-04: borrower02 logout
        assertTrue(output.contains("logged out"));

        // UC-01: borrower01 login
        assertTrue(output.contains("Welcome! borrower01"));

        // UC-03: book returned
        assertTrue(output.contains("Borrowed Books"));
        assertTrue(output.contains("Do you really want to return this book?"));
        assertTrue(output.contains("You returned a book!"));

        // UC-04: borrower01 logout
        assertTrue(output.contains("logged out"));

        // UC-01: borrower02 login
        assertTrue(output.contains("Welcome! borrower02"));

        // UC-02: borrow
        assertTrue(output.contains("book01 — author01  [Available]"));
        assertTrue(output.contains("You borrowed this book!"));

        // UC-04: borrower02 logout
        assertTrue(output.contains("logged out"));



    }
}
