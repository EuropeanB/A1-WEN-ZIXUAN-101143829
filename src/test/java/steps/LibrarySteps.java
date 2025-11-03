package steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

public class LibrarySteps {
    private boolean loginResult;

    @Given("a library system is running")
    public void login() {
        loginResult = false;
    }

    @When("the user logs in with username {string} and password {string}")
    public void check(String username, String password) {
        if (username.equals("alice") && password.equals("pass123")) {
            loginResult = true;
        }
    }

    @Then("login should be successful")
    public void success() {
        assertTrue(loginResult);
    }
}
