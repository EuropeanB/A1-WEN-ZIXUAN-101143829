package Library;

import java.util.ArrayList;


//  An array to store the borrower accounts
public class Accounts {
    private ArrayList<Borrower> list = new ArrayList<>();

    void add(Borrower b){
        list.add(b);
    }

    public int size(){
        return list.size();
    }

    public ArrayList<Borrower> all(){
        return list;
    }

}
