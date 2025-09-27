package Library;

// Book: Handing the book's parameters
public class Book {


    String title;
    String author;

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
}