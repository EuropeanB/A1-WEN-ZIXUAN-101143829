package Library;

import java.util.List;

public class Control {
    private Accounts accounts;
    private Authentication auth;
    //private Catalogue catalogue;
    private holdList holdList;
    private Screen screen;

    // launching
    public void launch() {
        init();
        run();
    }

    // initializing
    private void init() {
        accounts  = new InitializeAccounts().initializeAccounts();
        auth      = new Authentication(accounts);
        //catalogue = new InitializeLibrary().initializeLibrary();
        holdList  = new holdList();
        screen    = new Screen(System.in);
    }

    private void run() {
        while (true) {
            Borrower user = loginLoop();
            // temporary
            break;
        }
    }

    // login loop
    private Borrower loginLoop() {
        while (true) {
            String[] credential = screen.screenLogin();
            if (auth.login(credential[0], credential[1])) {
                System.out.println("Login Success!");
                System.out.println("Welcome! " + auth.getCurrentUser().getUsername());

                List<Book> available = holdList.bookAvailable(auth.getCurrentUser());
                screen.loginNotification(available);
                return auth.getCurrentUser();

            } else {
                System.out.println("Login failed! Please try again.\n");
            }
        }
    }

}
