package Library;

import java.util.ArrayList;

//  Catalogue: Handing the actions about books
public class Catalogue {
    ArrayList<Book> catalogue;

    // Initializing
    public Catalogue(){
        catalogue = new ArrayList<Book>();
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

}
