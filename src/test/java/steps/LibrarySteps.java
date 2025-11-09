package steps;
import Library.Accounts;
import Library.Authentication;
import Library.Book;
import Library.Borrower;
import Library.Catalogue;
import Library.holdList;
import Library.InitializeAccounts;
import Library.InitializeLibrary;
import Library.Recording;
import Library.Control;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarySteps {
    Accounts accounts;
    Catalogue catalogue;
    Borrower user1, user2, user3;
    Book book;
    Authentication auth;
    holdList holdlist;
    Control control;

    // Initialization
    @Given("The library system is initialized")
    public void Initialization() {
        accounts = new InitializeAccounts().initializeAccounts();
        auth = new Authentication(accounts);
        catalogue = new InitializeLibrary().initializeLibrary();
        holdlist = new holdList();
        control = new Control();
        control.simulateControl(accounts, auth, catalogue, holdlist);
    }

    @When("user {string} logs in")
    public void user_logs_in(String username) {
        Borrower user = accounts.getUser(username);
        assertNotNull(user);
        boolean success = auth.login(user.getUsername(), user.getPassword());
        assertTrue(success);
    }

    @When("user {string} logs out")
    public void user_logs_out(String username){
        Borrower user = accounts.getUser(username);
        assertEquals(user,auth.getCurrentUser());

        auth.logout();
        assertNull(auth.getCurrentUser());
    }

    // Simulate the borrowBook function in Control() class
    private boolean simulateBorrowBook(Borrower user, Book book) {
        if (book.getStatus() == Book.Status.Checked_out) {
            return false;
        }
        if (book.getStatus() == Book.Status.On_hold && holdlist.isOnHold(book, user)) {
            return false;
        }
        if (user.getBorrowedCount() >= 3) {
            return false;
        }

        control.processBorrow(user, book);
        return true;
    }

    @Then("user {string} can borrow the book {string} and borrowed it")
    public void borrowingSuccess(String username, String bookTitle) {
        Borrower user = accounts.getUser(username);
        assertNotNull(user);

        book = catalogue.getBook(bookTitle);
        assertNotNull(book);

        boolean success = simulateBorrowBook(user, book);
        assertTrue(success);
    }

    @Then("user {string} cannot borrow the book {string}")
    public void borrowingFail(String username, String bookTitle) {
        Borrower user = accounts.getUser(username);
        assertNotNull(user);

        book = catalogue.getBook(bookTitle);
        assertNotNull(book);

        boolean success = simulateBorrowBook(user, book);
        assertFalse(success);
    }

    // Simulate the returnBook function in Control() class
    private boolean simulateReturnBook(Borrower user, Book book) {
        //Impossible
        boolean hasBorrowed = user.getRecords()
                .stream()
                .anyMatch(r -> r.getBook().equals(book));
        if (!hasBorrowed) {
            return false;
        }
        if (book.getStatus() != Book.Status.Checked_out) {
            return false;
        }

        control.processReturn(user, book);
        return true;
    }

    @When("user {string} returns book {string}")
    public void returnBook(String username, String bookTitle) {
        Borrower user = accounts.getUser(username);
        assertNotNull(user);

        book = catalogue.getBook(bookTitle);
        assertNotNull(book);

        boolean success = simulateReturnBook(user, book);
        assertTrue(success);
    }

    @Then("user {string} can place a hold on book {string}'s queue")
    public void placeHold(String username, String bookTitle){
        Borrower user = accounts.getUser(username);
        assertEquals(auth.getCurrentUser(), user);
        Book book = catalogue.getBook(bookTitle);
        assertNotNull(book);

        assertFalse(holdlist.hasReserved(book,user));
        holdlist.placeHold(user, book);
        assertTrue(holdlist.hasReserved(book,user));
    }

    @Then("user {string} should be notified that {string} is available")
    public void hasNotification(String username, String bookTitle) {
        Borrower user = accounts.getUser(username);
        assertEquals(auth.getCurrentUser(), user);
        Book book = catalogue.getBook(bookTitle);
        assertNotNull(book);

        List<Book> books = holdlist.bookAvailable(user);
        assertTrue(!books.isEmpty());
    }

    @Then("user {string} should not be notified")
    public void noNotification(String username) {
        Borrower user = accounts.getUser(username);
        assertEquals(auth.getCurrentUser(), user);

        List<Book> books = holdlist.bookAvailable(user);
        assertTrue(books.isEmpty());
    }

    @Then("user {string} borrowed count is max")
    public void maxBorrowedCount(String username){
        Borrower user = accounts.getUser(username);
        assertEquals(3, user.getBorrowedCount());
    }

    @When ("user {string} borrowed count is {int}")
    public void getBorrowedCount(String username, int count){
        Borrower user = accounts.getUser(username);
        assertEquals(count, user.getBorrowedCount());
    }

    @Then("user {string} has {int} book that need to return")
    public void returnBookCount(String username, int count){
        Borrower user = accounts.getUser(username);
        List<Recording> records = user.getRecords();
        assertEquals(count, records.size());
    }

    @Then("user {string} has no book need to return")
    public void noReturnBook(String username){
        Borrower user = accounts.getUser(username);
        List<Recording> records = user.getRecords();
        assertTrue(records.isEmpty());
    }

    @Then("all books are available")
    public void allBookAvailable(){
        for(Book book : catalogue.allBooks()){
            assertEquals(Book.Status.Available, book.getStatus());
        }
    }

}
