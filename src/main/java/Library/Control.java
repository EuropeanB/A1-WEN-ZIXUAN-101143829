package Library;

import java.util.List;

public class Control {
    private Accounts accounts;
    private Authentication authentication;
    private Catalogue catalogue;
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
        catalogue = new InitializeLibrary().initializeLibrary();
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
                case 1 -> {
                    screen.showCatalogue(catalogue,authentication.getCurrentUser(),holdList);
                    screen.showBorrowedCount(authentication.getCurrentUser().getBorrowedCount());
                    borrowBook(authentication.getCurrentUser());
                }
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

    public void borrowBook(Borrower borrower){
        int bookIndex = screen.selectBook();
        Book book = catalogue.getBook(bookIndex);

        if(!screen.confirmBorrow(book)){
            System.out.println("borrowing cancelled");
            return;
        }

        if(book.getStatus() == Book.Status.Checked_out){
            System.out.println("You can't borrow this book!");
            System.out.println("Reason: This book has borrowed!");
            return;
        }

        System.out.println("You borrowed this book!");
    }

    // clear the text in console
    private void clearConsole(){
        for (int i = 0; i < 50; i++){
            System.out.println();
        }
    }

}
