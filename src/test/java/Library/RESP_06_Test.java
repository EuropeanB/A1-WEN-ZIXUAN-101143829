package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Logout process
// UC-04 1-3

public class RESP_06_Test {

    @Test
    @DisplayName("Check if current user session cleared")
    void RESP_06_test_01(){
        Accounts accounts = new InitializeAccounts().initializeAccounts();
        Authentication auth = new Authentication(accounts);

        Borrower borrower = accounts.all().get(0);
        assertTrue(auth.login(borrower.getUsername(), borrower.getPassword()));
        assertNotNull(auth.getCurrentUser());

        auth.logout();

        assertNull(auth.getCurrentUser());

    }
}
