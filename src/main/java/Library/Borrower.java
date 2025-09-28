package Library;

//  Borrower's account
public class Borrower {
    private final String username;
    private final String password;

    public Borrower(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }

}
