package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

//  Check the books and information

public class RESP_08_Test {
    @Test
    @DisplayName("Check the collection display")
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
}
