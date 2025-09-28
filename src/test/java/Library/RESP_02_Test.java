package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RESP_02_Test {

    @Test
    @DisplayName("Check the number of borrower")
    void RESP_02_test_01(){
        InitializeAccounts initializeaccounts = new InitializeAccounts();
        Accounts accounts = initializeaccounts.initializeAccounts();

        assertEquals(3, accounts.size());

        String[][] expected = {
                {"borrower01", "123"},
                {"borrower02",   "456"},
                {"borrower03", "789"}
        };

        for (int i = 0; i < expected.length; i++) {
            Borrower b = accounts.all().get(i);
            assertEquals(expected[i][0], b.getUsername());
            assertEquals(expected[i][1], b.getPassword());
        }
    }


}
