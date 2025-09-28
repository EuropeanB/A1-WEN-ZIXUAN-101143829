package Library;

//  Service: handling the login/out

public class Service {
    private final Accounts accounts;
    private Borrower currentUser;

    public Service(Accounts accounts){
        this.accounts = accounts;
    }

    public boolean login(String username, String password) {

        return false;
    }

    public Borrower getCurrentUser() {
        return currentUser;
    }
}
