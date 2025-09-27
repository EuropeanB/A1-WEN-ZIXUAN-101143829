package Library;

import java.util.ArrayList;

public class Catalogue {
    ArrayList<Book> catalogue;

    public Catalogue(){
        catalogue = new ArrayList<Book>();
    }

    public int getCatalogueSize(){
        return catalogue.size();
    }

}
