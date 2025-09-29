package Library;

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

    public String[] screenLogin(){

        System.out.println("Username: ");
        String username = scanner.nextLine();

        System.out.println("password: ");
        String password = scanner.nextLine();

        return new String[]{username, password};
    }

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

    public boolean confirmLogout() {
        System.out.print("Are you sure you want to logout? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        return confirm.equals("y");
    }

}
