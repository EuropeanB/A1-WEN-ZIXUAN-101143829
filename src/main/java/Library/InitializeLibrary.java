package Library;

// InitializeLibrary: Handing the initialization of library
public class InitializeLibrary {

    // Initialize catalogue
    Catalogue catalogue = new Catalogue();

    // Initialize the books
    public Catalogue initializeLibrary(){
        catalogue.addBook(new Book("book01", "author01"));
        catalogue.addBook(new Book("book02", "author02"));
        catalogue.addBook(new Book("book03", "author03"));
        catalogue.addBook(new Book("book04", "author04"));
        catalogue.addBook(new Book("book05", "author05"));
        catalogue.addBook(new Book("book06", "author06"));
        catalogue.addBook(new Book("book07", "author07"));
        catalogue.addBook(new Book("book08", "author08"));
        catalogue.addBook(new Book("book09", "author09"));
        catalogue.addBook(new Book("book10", "author10"));
        catalogue.addBook(new Book("book11", "author11"));
        catalogue.addBook(new Book("book12", "author12"));
        catalogue.addBook(new Book("book13", "author13"));
        catalogue.addBook(new Book("book14", "author14"));
        catalogue.addBook(new Book("book15", "author15"));
        catalogue.addBook(new Book("book16", "author16"));
        catalogue.addBook(new Book("book17", "author17"));
        catalogue.addBook(new Book("book18", "author18"));
        catalogue.addBook(new Book("book19", "author19"));
        catalogue.addBook(new Book("book20", "author20"));
        return catalogue;
    }
}
