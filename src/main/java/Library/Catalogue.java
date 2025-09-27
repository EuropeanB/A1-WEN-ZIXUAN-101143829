package Library;

import java.util.ArrayList;

//  Catalogue: Handing the actions about books
public class Catalogue {
    ArrayList<Book> catalogue;

    public Catalogue(){
        catalogue = new ArrayList<Book>();
    }

    public int getCatalogueSize(){
        return catalogue.size();
    }

    public void addBook(Book book){
        catalogue.add(book);
    }

    Book getBook(int index){
        return null ;
    }

}
