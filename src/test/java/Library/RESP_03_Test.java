package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

}
