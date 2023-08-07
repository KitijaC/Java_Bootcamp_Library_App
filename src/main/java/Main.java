import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();
        Scanner scanner = new Scanner(System.in);

        librarySystem = LibrarySystem.loadFromCSV("library_data.ser");
        if (librarySystem == null) {
            librarySystem = new LibrarySystem();
        }

        while (true) {
            System.out.println("Welcome to the Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Register User");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Get Statistics");
            System.out.println("7. Search Books");
            System.out.println("8. Display All Available Books");
            System.out.println("9. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter total copies: ");
                    int totalCopies = scanner.nextInt();
                    librarySystem.addBook(title, author, totalCopies);
                    break;
                case 2:
                    System.out.print("Enter book title: ");
                    String removeBookTitle = scanner.nextLine();
                    Book bookToRemove = findBookByTitle(librarySystem, removeBookTitle);
                    if (bookToRemove == null) {
                        System.out.println("Book not found.");
                    } else {
                        librarySystem.removeBook(bookToRemove);
                        System.out.println("Book removed from the library.");
                    }
                    break;
                case 3:
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    librarySystem.registerUser(userName);
                    break;
                case 4:
                    System.out.print("Enter borrower's name: ");
                    String borrowerName = scanner.nextLine();
                    User borrower = findUserByName(librarySystem, borrowerName);
                    if (borrower == null) {
                        System.out.println("User not found.");
                    } else {
                        System.out.print("Enter book title: ");
                        String bookTitle = scanner.nextLine();
                        Book requestedBook = findBookByTitle(librarySystem, bookTitle);
                        if (requestedBook == null) {
                            System.out.println("Book not found.");
                        } else {
                            librarySystem.borrowBook(borrower, requestedBook);
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter returner's name: ");
                    String returnerName = scanner.nextLine();
                    User returner = findUserByName(librarySystem, returnerName);
                    if (returner == null) {
                        System.out.println("User not found.");
                    } else {
                        System.out.print("Enter book title: ");
                        String returnedBookTitle = scanner.nextLine();
                        Book returnedBook = findBookByTitle(librarySystem, returnedBookTitle);
                        if (returnedBook == null) {
                            System.out.println("Book not found.");
                        } else {
                            librarySystem.returnBook(returner, returnedBook);
                        }
                    }
                    break;

                case 6:
                    librarySystem.getStatistics();
                    break;
                case 7:
                    System.out.print("Enter search keyword: ");
                    String searchKeyword = scanner.nextLine();
                    List<Book> searchResults = librarySystem.searchBooks(searchKeyword);

                    if (searchResults.isEmpty()) {
                        System.out.println("No books found matching the search keyword.");
                    } else {
                        System.out.println("Search Results:");
                        for (Book book : searchResults) {
                            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() +
                                    ", Available Copies: " + book.getAvailableCopies());
                        }
                    }
                    break;
                case 8:
                    librarySystem.getAvailableBooks();
                    break;
                case 9:
                    librarySystem.saveToCSV("library_data.ser");
                    System.out.println("Library data saved. Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static User findUserByName(LibrarySystem librarySystem, String borrowerName) {
        for (User user : librarySystem.getUsers()) {
            if (user.getName().equalsIgnoreCase(borrowerName)) {
                return user;
            }
        }
        return null;
    }


    private static Book findBookByTitle(LibrarySystem librarySystem, String title) {
        for (Book book : librarySystem.getBooks()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
}
