package Library;

import java.time.LocalDate;
import java.util.Optional;

// Book: Handing the book's parameters
public class Book {


    private final String title;
    private final String author;

    public enum Status { Available, Checked_out, On_hold }
    private Status status = Status.Available;

    private Optional<LocalDate> dueDate = Optional.empty();


    // Book initialized
    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }

    // getters
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public Status getStatus(){
        return status;
    }
    public Optional<LocalDate> getDueDate() {
        return dueDate;
    }
    // Output the due date
    public String getDueDateText(){
        return dueDate.map(LocalDate::toString).orElse("-");
    }

    // setters
    public void setStatus(Status status){
        this.status = status;
        if (status != Status.Checked_out) {
            dueDate = Optional.empty();
        }
    }
    public void setDueDate(LocalDate date){
        this.dueDate = Optional.ofNullable(date);
    }
}