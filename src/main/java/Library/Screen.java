package Library;

import java.util.Scanner;
import java.io.InputStream;

public class Screen {
    private final Scanner scanner;

    public Screen() {
        this(System.in); // 默认用真实输入
    }

    public Screen(InputStream in) {
        this.scanner = new Scanner(in); // 测试时可以换成模拟输入流
    }

    public String[] screenLogin(){
        return new String[0];
    }


}
