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

public class RESP_10_Test {
    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    void setUp() {
        // 备份原始输入输出
        originalIn = System.in;
        originalOut = System.out;

        // 准备捕获输出
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
    }

    @AfterEach
    void tearDown() {
        // 恢复原始输入输出
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Check single book information display")
    public void RESP_10_test_01() {
        String input = "borrower02\n456\n1\n20\n";
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

        assertTrue(output.contains("Book Information"));
    }

}
