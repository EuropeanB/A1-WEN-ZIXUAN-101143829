package Library;

import java.time.LocalDate;

public class Recording {
    private final Book book;
    private final LocalDate dueDate;

    public Recording(Book book, LocalDate dueDate){
        this.book = book;
        this.dueDate = dueDate;
    }

    // getters
    public Book getBook(){
        return null;
    }
    public LocalDate getDueDate(){
        return null;
    }

}
