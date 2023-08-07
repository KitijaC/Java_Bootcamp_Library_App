import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibrarySystem implements Serializable {

    private List<Book> books;
    private List<User> users;
    private Map<User, Integer> userBorrowedBooksCount;


    public LibrarySystem() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.userBorrowedBooksCount = new HashMap<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }


    public void addBook(String title, String author, int totalCopies){
        Book book = new Book(title, author, totalCopies);
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void registerUser(String name) {
        User user = new User(name);
        users.add(user);
        userBorrowedBooksCount.put(user, 0);
    }

    public void borrowBook(User user, Book book) {
        if (!canUserBorrow(user)) {
            System.out.println("User cannot borrow more books.");
            return;
        }

        if (!canUserBorrowMoreCopies(user, book)) {
            System.out.println("User cannot borrow more copies of the same book.");
            return;
        }

        if (book.getAvailableCopies() > 0) {
            userBorrowedBooksCount.put(user, userBorrowedBooksCount.get(user) + 1);
            book.decreaseAvailableCopies();
            user.borrowBook(book);
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("No available copies of the book.");
        }
    }

    public void returnBook(User user, Book book) {
        if (user.hasBorrowedBook(book)) {
            user.returnBook(book);
            book.increaseAvailableCopies();
            userBorrowedBooksCount.put(user, userBorrowedBooksCount.get(user) - 1);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("User has not borrowed this book.");
        }
    }

    public void getStatistics() {
        for (Book book : books) {
            System.out.println(book.getTitle() + " by " + book.getAuthor() +
                    " - Total Copies: " + book.getTotalCopies() +
                    ", Available Copies: " + book.getAvailableCopies());
        }
    }

    public void getAvailableBooks() {
        System.out.println("Available Books:");
        for (Book book : books) {
            if (book.getAvailableCopies() > 0) {
                System.out.println(book.getTitle() + " by " + book.getAuthor() +
                        " - Available Copies: " + book.getAvailableCopies());
            }
        }
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> searchResults = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                searchResults.add(book);
            }
        }
        return searchResults;
    }

    public void saveToCSV(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            writer.println("Title,Author,Total Copies,Available Copies");

            for (Book book : books) {
                writer.println(book.getTitle() + "," + book.getAuthor() + ","
                        + book.getTotalCopies() + "," + book.getAvailableCopies());
            }

            for (User user : users) {
                writer.println(user.getName());
            }


            for (Map.Entry<User, Integer> entry : userBorrowedBooksCount.entrySet()) {
                User user = entry.getKey();
                int count = entry.getValue();
                writer.println(user.getName() + "," + count);
            }

            System.out.println("Library data saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LibrarySystem loadFromCSV(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (LibrarySystem) ois.readObject();
        } catch (FileNotFoundException e) {
            return new LibrarySystem();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean canUserBorrow(User user) {
        return userBorrowedBooksCount.getOrDefault(user, 0) < 5;
    }

    private boolean canUserBorrowMoreCopies(User user, Book book) {
        return user.getBorrowedBooks().stream().noneMatch(b -> b.equals(book));
    }

}
