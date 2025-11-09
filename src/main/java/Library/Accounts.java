package Library;

import java.util.ArrayList;


//  An array to store the borrower accounts
public class Accounts {

    // A list when initializing
    private ArrayList<Borrower> list = new ArrayList<>();

    // Add the borrower when initializing
    void add(Borrower b){
        list.add(b);
    }

    // return the size of list
    public int size(){
        return list.size();
    }

    // return all borrowers
    public ArrayList<Borrower> all(){
        return list;
    }

    // Find the specific borrower username
    public Borrower getUser(String username) {
        for (Borrower b : list) {
            if (b.getUsername().equals(username)) {
                return b;
            }
        }
        return null;
    }

}
