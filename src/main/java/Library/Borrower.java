package Library;

//  Borrower's account
public class Borrower {
    private final String username;
    private final String password;
    private int borrowedCount = 0;

    // Initializing
    public Borrower(String username, String password){
        this.username = username;
        this.password = password;
    }

    // getters
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public int getBorrowedCount(){
        return -1;
    }

}
