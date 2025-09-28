package Library;

import java.util.Scanner;
import java.io.InputStream;
import java.util.List;

public class Screen {
    private final Scanner scanner;

    public Screen() {
        this(System.in); // 默认用真实输入
    }

    public Screen(InputStream in) {
        this.scanner = new Scanner(in); // 测试时可以换成模拟输入流
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

}
