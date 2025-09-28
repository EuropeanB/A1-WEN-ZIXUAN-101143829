package Library;

//  Service: handling the login/out

public class Authentication {
    private final Accounts accounts;
    private Borrower currentUser;

    public Authentication(Accounts accounts){
        this.accounts = accounts;
    }

    public boolean login(String username, String password) {
        for (Borrower borrower : accounts.all()) {
            if (borrower.getUsername().equals(username) && borrower.getPassword().equals(password)) {
                currentUser = borrower;
                return true;
            }
        }
        return false;
    }

    public Borrower getCurrentUser() {
        return currentUser;
    }
}
