package Library;

import java.time.LocalDate;
import java.util.Scanner;
import java.io.InputStream;
import java.util.List;

public class Screen {
    private final Scanner scanner;

    public Screen() {
        this(System.in);
    }

    // For testing
    public Screen(InputStream in) {
        this.scanner = new Scanner(in);
    }

    // login screen
    public String[] screenLogin(){

        System.out.println("Username: ");
        String username = scanner.nextLine();

        System.out.println("password: ");
        String password = scanner.nextLine();

        return new String[]{username, password};
    }

    // The notification after login
    public void loginNotification(List<Book> books){
        if(!books.isEmpty()){
            System.out.println("The books returned and you can borrow right now!");
            for (Book b : books) {
                System.out.println("- " + b.getTitle() + " by " + b.getAuthor());
            }
        } else{
            System.out.println("There is no any notification...");
        }
    }

    //  Main menu display
    public int MainMenu() {
        System.out.println("\nPlease select：");
        System.out.println("1. Borrow");
        System.out.println("2. Return");
        System.out.println("3. Log out");
        System.out.print("Type 1/2/3: ");
        while (true) {
            String s = scanner.nextLine().trim();
            try {
                int c = Integer.parseInt(s);
                if (c >= 1 && c <= 3) return c;
            } catch (NumberFormatException ignored) {}
            System.out.print("Invalid input, try again!!!\n ");
        }
    }

    // To confirm if user want to logout
    public boolean confirmLogout() {
        System.out.print("Are you sure you want to logout? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        return confirm.equals("y");
    }

    // Display the borrowed count
    public void showBorrowedCount(int count){
        System.out.println("You already borrowed " + count + "/3 books!");
    }

    // Display the all books
    public void showCatalogue(Catalogue catalogue,Borrower currentUser, holdList holds) {
        System.out.println("\n--------- Library Collection ---------");
        List<Book> books = catalogue.allBooks();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);

            String statusText = "";
            switch (book.getStatus()) {
                case Available -> statusText = "Available";
                case Checked_out -> statusText = "Checked Out";
                case On_hold -> {
                    if (!holds.isOnHold(book, currentUser)) {
                        statusText = "Available";
                    } else {
                        statusText = "On Hold";
                    }
                }
            }

            String dueDate = "";
            if (book.getStatus() == Book.Status.Checked_out && book.getDueDate().isPresent()) {
                dueDate = "  (Due: " + book.getDueDateText() + ")";
            }

            System.out.printf("%2d: %s — %s  [%s]%s%n",
                    i + 1, book.getTitle(), book.getAuthor(), statusText, dueDate);
        }
        System.out.println("---------------------------------------\n");
    }

    // Select book during borrowing
    public int selectBook() {
        System.out.println("\nWhich book you would like to borrow? (1-20): ");
        while (true) {
            String s = scanner.nextLine().trim();
            try {
                int index = Integer.parseInt(s);
                if (index >= 1 && index <= 20) {
                    return index - 1;
                } else {
                    System.out.println("Invalid range, please enter a number between 1 and 20!");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("Invalid input, try again!");
            }
        }
    }

    // Last borrowing confirmation
    public boolean borrowChecking(Book book){
        System.out.println("---------- Book Information ----------");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Status: " + book.getStatus());
        //System.out.println("DueDate: " + book.getDueDateText());
        System.out.println("---------------- END -----------------");
        System.out.println("You want to borrow this book? y/n: ");
        String choice = scanner.nextLine().trim().toLowerCase();
        return choice.equals("y");
    }

    // The last confirmation in borrowing
    public boolean borrowConfirm(Book book){
        LocalDate today = LocalDate.now();
        LocalDate expectedDue = today.plusDays(14);

        System.out.println("---------- Book Information ----------");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("DueDate: " + expectedDue);
        System.out.println("---------------- END -----------------");
        System.out.println("Confirm this borrowing? y/n: ");
        String choice = scanner.nextLine().trim().toLowerCase();
        return choice.equals("y");
    }

    // Book reservation
    public boolean bookBooking(Book book) {
        System.out.println("Would you like to place a hold for this book? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        return choice.equals("y");
    }

}
