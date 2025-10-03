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

// Check the last borrow checking
// UC-02: 11-13

public class RESP_13_Test {
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
    @DisplayName("Check the detail output before last confirmation")
    public void RESP_13_test_01(){
        String input = "borrower02\n456\n1\n20\ny\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            new Control().launch();
        } catch (Exception ignored) {

        } finally{
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String output = capturedOut.toString();
        System.out.println("------------ Output -------------");
        System.out.println(output);
        System.out.println("---------------------------------");

        assertTrue(output.contains("DueDate: "));
    }

    @Test
    @DisplayName("Check the last confirmation output")
    public void RESP_13_test_02(){
        String input = "borrower02\n456\n1\n20\ny\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            new Control().launch();
        } catch (Exception ignored) {

        } finally{
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String output = capturedOut.toString();
        System.out.println("------------ Output -------------");
        System.out.println(output);
        System.out.println("---------------------------------");

        assertTrue(output.contains("Confirm this borrowing?"));
        assertTrue(output.contains("You borrowed this book!"));
    }



}
