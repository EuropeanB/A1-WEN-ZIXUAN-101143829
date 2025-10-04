package Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//  System book collection initialization
//  UC-01 preconditions

public class RESP_01_Test {

    @Test
    @DisplayName("Check library catalogue size is 20")
    void RESP_01_test_01(){
        // Initialization
        InitializeLibrary library = new InitializeLibrary();
        Catalogue catalogue = library.initializeLibrary();

        int size = catalogue.getCatalogueSize();

        assertEquals(20, size);
    }

    @Test
    @DisplayName("Check the first book title in catalogue")
    void RESP_01_test_02(){
        // Initialization
        InitializeLibrary library = new InitializeLibrary();
        Catalogue catalogue = library.initializeLibrary();

        Book book = catalogue.getBook(0);
        String title = book.getTitle();

        assertEquals("book01", title);
    }

    @Test
    @DisplayName("Check the first book Author in catalogue")
    void RESP_01_test_03(){
        // Initialization
        InitializeLibrary library = new InitializeLibrary();
        Catalogue catalogue = library.initializeLibrary();

        Book book = catalogue.getBook(19);
        String author = book.getAuthor();

        assertEquals("author20", author);
    }

    @Test
    @DisplayName("Check the books' Status")
    void RESP_01_test_04(){
        // Initialization
        InitializeLibrary library = new InitializeLibrary();
        Catalogue catalogue = library.initializeLibrary();

        for (int i = 0; i < catalogue.getCatalogueSize(); i++) {
            assertEquals(Book.Status.Available, catalogue.getBook(i).getStatus());
        }
    }

    @Test
    @DisplayName("Check the books' due date")
    void RESP_01_test_05(){
        // Initialization
        InitializeLibrary library = new InitializeLibrary();
        Catalogue catalogue = library.initializeLibrary();

        for (int i = 0; i < catalogue.getCatalogueSize(); i++) {
            assertTrue(catalogue.getBook(i).getDueDate().isEmpty());
            assertEquals("-", catalogue.getBook(i).getDueDateText());
        }
    }

}
