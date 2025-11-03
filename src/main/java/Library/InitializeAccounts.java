package Library;

// InitializeAccounts: Handing the initialization of accounts
public class InitializeAccounts {

    // Initializing
    public Accounts initializeAccounts(){
        Accounts accounts = new Accounts();
        accounts.add(new Borrower("alice", "pass123"));
        accounts.add(new Borrower("bob", "pass456"));
        accounts.add(new Borrower("charlie", "pass789"));
        return accounts;
    }

}
