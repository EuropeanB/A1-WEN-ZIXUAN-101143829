package Library;

import java.util.List;
import java.time.LocalDate;

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
                case 2 -> returnBook(authentication.getCurrentUser());
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

    // borrowing process
    public void borrowBook(Borrower borrower){
        int bookIndex = screen.selectBook();
        Book book = catalogue.getBook(bookIndex);

        // Cancelled manually
        if(!screen.borrowChecking(book)){
            System.out.println("borrowing cancelled");
            return;
        }

        // If the status is check_out
        if(book.getStatus() == Book.Status.Checked_out){
            System.out.println("You can't borrow this book!");
            System.out.println("Reason: This book has borrowed!");

            if (screen.bookBooking(book)) {
                for (Recording r : borrower.getRecords()) {
                    if (r.getBook().equals(book)) {
                        System.out.println("You already borrowed this book!");
                        return;
                    }
                }
                if (holdList.hasReserved(book, borrower)) {
                    System.out.println("You have already reserved this book!");
                    return;
                }
                holdList.placeHold(borrower, book);
                System.out.println("Hold placed successfully! You will be notified when it becomes available.");
            } else {
                System.out.println("No hold placed.");
            }

            return;
        }

        // If the status is on hold and user is not the first person in the queue
        if (book.getStatus() == Book.Status.On_hold && holdList.isOnHold(book, borrower)) {
            System.out.println("You can't borrow this book!");
            System.out.println("Reason: This book is on hold!");

            if (screen.bookBooking(book)) {
                if (holdList.hasReserved(book, borrower)) {
                    System.out.println("You have already reserved this book!");
                    return;
                }
                holdList.placeHold(borrower, book);
                System.out.println("Hold placed successfully! You will be notified when it becomes available.");
            } else {
                System.out.println("No hold placed.");
            }

            return;
        }

        // If user borrowed 3 books before
        if(borrower.getBorrowedCount() >= 3){
            System.out.println("You can't borrow this book!");
            System.out.println("Reason: You already borrowed 3 books! ");

            if (screen.bookBooking(book)) {
                if (holdList.hasReserved(book, borrower)) {
                    System.out.println("You have already reserved this book!");
                    return;
                }
                holdList.placeHold(borrower, book);
                System.out.println("Hold placed successfully! You will be notified when it becomes available.");
            } else {
                System.out.println("No hold placed.");
            }

            return;
        }

        //System.out.println("You borrowed this book!");
        if(screen.borrowConfirm(book)){
            LocalDate due = LocalDate.now().plusDays(14);
            borrower.addRecord(new Recording(book,due));

            book.setStatus(Book.Status.Checked_out);
            book.setDueDate(due);
            borrower.increaseBorrowedCount();

            System.out.println("You borrowed this book!");
        } else {
            System.out.println("borrowing cancelled!");
            return;
        }

    }

    // Handling the return book process
    public void returnBook(Borrower borrower){
        screen.showBorrowedBooks(borrower);
    }

    // Unused
    // clear the text in console
    private void clearConsole(){
        for (int i = 0; i < 50; i++){
            System.out.println();
        }
    }

}
