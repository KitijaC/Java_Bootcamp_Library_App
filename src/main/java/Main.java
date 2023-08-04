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
            System.out.println("8. Exit");

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
                    // Borrow Book
                    break;
                case 5:
                    // Return Book
                    break;
                case 6:
                    librarySystem.getStatistics();
                    break;
                case 7:
                    System.out.print("Enter search keyword: ");
                    String keyword = scanner.nextLine();
                    librarySystem.searchBooks(keyword);
                    break;
                case 8:
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

    private static Book findBookByTitle(LibrarySystem librarySystem, String title) {
        for (Book book : librarySystem.getBooks()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
}
