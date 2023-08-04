import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibrarySystem {

    private List<Book> books;
    private List<User> users;
    private Map<User, Integer> userBorrowedBooksCount;


    public LibrarySystem() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.userBorrowedBooksCount = new HashMap<>();
    }

    public void addBook(String title, String author, int totalCopies){
        Book book = new Book(title, author, totalCopies);
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }



}
