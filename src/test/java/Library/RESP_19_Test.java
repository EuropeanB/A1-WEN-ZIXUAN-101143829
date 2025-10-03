package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

// Check the return book selection amd confirmation
// UC-03: 2,5,6,7

public class RESP_19_Test {

    @Test
    @DisplayName("Check the return book selection")
    public void RESP_19_test_01() {
        // 构造测试数据
        Borrower borrower = new Borrower("borrower1", "123");
        Book book1 = new Book("Book01", "Author01");
        Book book2 = new Book("Book02", "Author02");

        borrower.addRecord(new Recording(book1, LocalDate.now().plusDays(7)));
        borrower.addRecord(new Recording(book2, LocalDate.now().plusDays(10)));

        // 模拟输入 "2" → 选择第二本书归还
        String input = "2\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Screen screen = new Screen(in);

        int selectedIndex = screen.selectBorrowedBook(borrower);

        // 验证返回索引正确（选择第2本 → 返回1）
        assertEquals(1, selectedIndex);
    }

    @Test
    @DisplayName("Step 5: Confirm returning a book")
    public void RESP_19_test_02() {
        Book book = new Book("Book01", "Author01");

        String input = "y\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Screen screen = new Screen(in);

        boolean confirm = screen.confirmReturn(book);

        // 验证确认结果为 true
        assertTrue(confirm);
    }

}
