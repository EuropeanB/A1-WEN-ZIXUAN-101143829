package Library;

import java.util.ArrayList;
import java.util.List;

//  Catalogue: Handing the actions about books
public class Catalogue {
    ArrayList<Book> catalogue;

    // Initializing
    public Catalogue(){
        catalogue = new ArrayList<Book>();
    }

    // Display the books
    public List<Book> allBooks(){
        return catalogue;
    }

    // adders
    public void addBook(Book book){
        catalogue.add(book);
    }

    // getters
    public int getCatalogueSize(){
        return catalogue.size();
    }
    public Book getBook(int index){
        return catalogue.get(index);
    }
    public Book getBook(String title) {
        for (Book b : catalogue) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                return b;
            }
        }
        return null;
    }

}
