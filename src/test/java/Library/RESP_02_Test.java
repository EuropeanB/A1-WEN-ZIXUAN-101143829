package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RESP_02_Test {

    @Test
    @DisplayName("Check the amounts of borrower")
    void RESP_02_test_01(){
        InitializeAccounts initializeaccounts = new InitializeAccounts();
        Accounts accounts = initializeaccounts.initializeAccounts();
        assertEquals(3, accounts.size());
    }

}
