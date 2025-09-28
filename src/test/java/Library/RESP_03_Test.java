package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RESP_03_Test {

    @Test
    @DisplayName("Check the login system")
    void RESP_03_test_01(){

        Accounts accounts = new InitializeAccounts().initializeAccounts();
        Service service = new Service(accounts);

        boolean result01 = service.login("borrower01", "123");
        assertTrue(result01);

        boolean result02 = service.login("borrower01","1234");
        assertFalse(result02);

        boolean result03 = service.login("borrower04","123");
        assertFalse(result03);
    }

}
