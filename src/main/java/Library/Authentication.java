package Library;

//  Service: handling the login/out

public class Authentication {
    private final Accounts accounts;
    private Borrower currentUser;

    public Authentication(Accounts accounts){
        this.accounts = accounts;
    }

    public boolean login(String username, String password) {
        for (Borrower b : accounts.all()) {
            if (b.getUsername().equals(username) && b.getPassword().equals(password)) {
                currentUser = b;
                return true;
            }
        }
        return false;
    }

    public Borrower getCurrentUser() {
        return currentUser;
    }
}
