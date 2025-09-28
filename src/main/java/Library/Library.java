package Library;

// Control the whole program

public class Library {
    public static void main(String[] args){
        //  Initializing
        Accounts accounts = new InitializeAccounts().initializeAccounts();
        Authentication authentication = new Authentication(accounts);
        Screen screen = new Screen(System.in);

        // Identification
        String[] credential = screen.screenLogin();

        //  Success or Failed
        if(authentication.login(credential[0], credential[1])){
            System.out.println("login Success!");
            System.out.println("Welcome! " + authentication.getCurrentUser().getUsername());
        } else {
            System.out.println("login failed!");
        }

    }
}
