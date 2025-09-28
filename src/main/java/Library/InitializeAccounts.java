package Library;

public class InitializeAccounts {

    public Accounts initializeAccounts(){
        Accounts accounts = new Accounts();
        accounts.add(new Borrower("borrower01", "123"));
        accounts.add(new Borrower("borrower02", "456"));
        accounts.add(new Borrower("borrower03", "789"));
        return accounts;
    }

}
