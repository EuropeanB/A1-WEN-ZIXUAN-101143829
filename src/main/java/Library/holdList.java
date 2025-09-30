package Library;

import java.util.*;

public class holdList {
    private final Map<Book, Queue<Borrower>> holds = new HashMap<>();

    public void placeHold(Borrower borrower, Book book){
        Queue<Borrower> queue = holds.get(book);
        if (queue == null){
            queue = new ArrayDeque<>();
            holds.put(book, queue);
        }
        queue.add(borrower);

    }

    // Check if books is available
    public List<Book> bookAvailable(Borrower borrower){
        List<Book> result = new ArrayList<>();
        for(Map.Entry<Book, Queue<Borrower>> entry: holds.entrySet()){
            Book book = entry.getKey();
            Queue<Borrower> queue = entry.getValue();

            if(book.getStatus() == Book.Status.Available &&
                    queue != null &&
                    !queue.isEmpty() &&
                    queue.peek().equals(borrower)){
                result.add(book);
            }
        }
        return result;
    }

    public boolean isOnHold(Book book, Borrower borrower) {
        Queue<Borrower> queue = holds.get(book);
        if (queue == null || queue.isEmpty()) {
            return false;
        }
        Borrower first = queue.peek();

        if (first.equals(borrower)) {
            return false;
        }
        return true;
    }

}
