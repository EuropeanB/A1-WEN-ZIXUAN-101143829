package Library;

import java.util.List;

public class Control {
    private Accounts accounts;
    private Authentication authentication;
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
        accounts       = new InitializeAccounts().initializeAccounts();
        authentication = new Authentication(accounts);
        //catalogue = new InitializeLibrary().initializeLibrary();
        holdList       = new holdList();
        screen         = new Screen(System.in);
    }

    // Main function
    private void run() {
        while (true) {
            Borrower user = loginLoop();
            mainMenuLoop(user);
        }
    }

    // login loop
    private Borrower loginLoop() {
        while (true) {
            String[] credential = screen.screenLogin();
            if (authentication.login(credential[0], credential[1])) {
                System.out.println("Login Success!");
                System.out.println("Welcome! " + authentication.getCurrentUser().getUsername());

                List<Book> available = holdList.bookAvailable(authentication.getCurrentUser());
                screen.loginNotification(available);
                return authentication.getCurrentUser();

            } else {
                System.out.println("Login failed! Please try again.\n");
            }
        }
    }

    // Main menu
    private void mainMenuLoop(Borrower user) {
        boolean inSession = true;
        while (inSession) {
            int choice = screen.MainMenu();
            switch (choice) {
                case 1 -> screen.showBorrowedCount(authentication.getCurrentUser().getBorrowedCount());
                case 2 -> System.out.println("not yet\n");
                case 3 -> {
                    if(screen.confirmLogout()){
                        authentication.logout();
                        //clearConsole();
                        System.out.println("logged out");
                        inSession = false;
                    } else {
                        System.out.println("logout cancelled");
                    }
                }
                default -> System.out.println("Invalid input, try again!!!\n");
            }
        }
    }

    private void clearConsole(){
        for (int i = 0; i < 50; i++){
            System.out.println();
        }
    }

}
