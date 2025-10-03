package Library;

import java.util.ArrayList;
import java.util.List;

//  Borrower's account
public class Borrower {
    private final String username;
    private final String password;
    private int borrowedCount = 0;
    private List<Recording> records = new ArrayList<>();

    // Initializing
    public Borrower(String username, String password){
        this.username = username;
        this.password = password;
    }

    // getters
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public int getBorrowedCount(){
        return borrowedCount;
    }
    public List<Recording> getRecords() {
        return new ArrayList<>(records);
    }

    // setters
    public void increaseBorrowedCount(){
        borrowedCount += 1;
    }
    public void decreaseBorrowedCount(){
        borrowedCount -=1;
    }

    // handling the system recording
    public void addRecord(Recording record) {
        records.add(record);
    }
    public void removeRecord(Book book) {
        records.removeIf(r -> r.getBook().equals(book));
        decreaseBorrowedCount();
    }

}
