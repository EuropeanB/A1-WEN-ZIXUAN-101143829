package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

//  User Authentication and credentials validation
//  UC-01 1,2,3

public class RESP_03_Test {

    @Test
    @DisplayName("Check the login system")
    void RESP_03_test_01(){

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
    @DisplayName("Check the login process")
    void RESP_03_test_02(){
        String input = "borrower01\n123\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Control control = new Control();
        control.launch();

        String output = out.toString();

        assertTrue(output.contains("Success!"));
        assertTrue(output.contains("borrower01"));


    }

}
