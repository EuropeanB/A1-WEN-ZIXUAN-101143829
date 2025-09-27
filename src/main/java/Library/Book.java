package Library;

// Book: Handing the book's parameters
public class Book {


    private final String title;
    private final String author;

    public enum Status { Available, Checked_out, On_hold }
    private Status status = Status.Available;


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
        return null;
    }
}