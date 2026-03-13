// Q5. Doubly Linked List: Library Management System

class Q5_LibraryManagementSystem {

    static class Node {
        String bookTitle;
        String author;
        String genre;
        int bookId;
        boolean available; // availability status
        Node next;
        Node prev;

        Node(String bookTitle, String author, String genre, int bookId, boolean available) {
            this.bookTitle = bookTitle;
            this.author = author;
            this.genre = genre;
            this.bookId = bookId;
            this.available = available;
            this.next = null;
            this.prev = null;
        }
    }

    static class LibraryDoublyList {
        Node head;
        Node tail;
        int count;

        LibraryDoublyList() {
            head = null;
            tail = null;
            count = 0;
        }

        // Add at beginning
        void addAtBeginning(String title, String author, String genre, int bookId, boolean available) {
            Node newNode = new Node(title, author, genre, bookId, available);
            if (head == null) {
                head = tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            count++;
            System.out.println("Book added at beginning: " + title);
        }

        // Add at end
        void addAtEnd(String title, String author, String genre, int bookId, boolean available) {
            Node newNode = new Node(title, author, genre, bookId, available);
            if (tail == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            count++;
            System.out.println("Book added at end: " + title);
        }

        // Add at specific position (1-based)
        void addAtPosition(String title, String author, String genre, int bookId, boolean available, int position) {
            if (position <= 1) {
                addAtBeginning(title, author, genre, bookId, available);
                return;
            }
            Node temp = head;
            for (int i = 1; i < position - 1 && temp != null; i++) temp = temp.next;
            if (temp == null || temp.next == null) {
                addAtEnd(title, author, genre, bookId, available);
                return;
            }
            Node newNode = new Node(title, author, genre, bookId, available);
            newNode.next = temp.next;
            newNode.prev = temp;
            if (temp.next != null) temp.next.prev = newNode;
            temp.next = newNode;
            count++;
            System.out.println("Book added at position " + position + ": " + title);
        }

        // Remove by book ID
        void removeByBookId(int bookId) {
            if (head == null) {
                System.out.println("Library is empty.");
                return;
            }
            Node temp = head;
            while (temp != null) {
                if (temp.bookId == bookId) {
                    if (temp.prev != null) temp.prev.next = temp.next;
                    else head = temp.next;
                    if (temp.next != null) temp.next.prev = temp.prev;
                    else tail = temp.prev;
                    temp.next = null;
                    temp.prev = null;
                    count--;
                    System.out.println("Removed book: " + temp.bookTitle);
                    return;
                }
                temp = temp.next;
            }
            System.out.println("Book with ID " + bookId + " not found.");
        }

        // Search by title
        void searchByTitle(String title) {
            System.out.println("\n--- Search by Title: " + title + " ---");
            boolean found = false;
            Node temp = head;
            while (temp != null) {
                if (temp.bookTitle.equalsIgnoreCase(title)) {
                    System.out.println("Found -> " + formatBook(temp));
                    found = true;
                }
                temp = temp.next;
            }
            if (!found) System.out.println("Book titled '" + title + "' not found.");
        }

        // Search by author
        void searchByAuthor(String author) {
            System.out.println("\n--- Books by Author: " + author + " ---");
            boolean found = false;
            Node temp = head;
            while (temp != null) {
                if (temp.author.equalsIgnoreCase(author)) {
                    System.out.println("Found -> " + formatBook(temp));
                    found = true;
                }
                temp = temp.next;
            }
            if (!found) System.out.println("No books found by author: " + author);
        }

        // Update availability status by book ID
        void updateAvailability(int bookId, boolean status) {
            Node temp = head;
            while (temp != null) {
                if (temp.bookId == bookId) {
                    System.out.println("Updated availability for '" + temp.bookTitle + "': "
                            + temp.available + " -> " + status);
                    temp.available = status;
                    return;
                }
                temp = temp.next;
            }
            System.out.println("Book with ID " + bookId + " not found.");
        }

        // Display forward
        void displayForward() {
            if (head == null) {
                System.out.println("Library is empty.");
                return;
            }
            System.out.println("\n--- Books (Forward) ---");
            Node temp = head;
            int index = 1;
            while (temp != null) {
                System.out.println(index++ + ". " + formatBook(temp));
                temp = temp.next;
            }
            System.out.println("-----------------------");
        }

        // Display reverse
        void displayReverse() {
            if (tail == null) {
                System.out.println("Library is empty.");
                return;
            }
            System.out.println("\n--- Books (Reverse) ---");
            Node temp = tail;
            int index = 1;
            while (temp != null) {
                System.out.println(index++ + ". " + formatBook(temp));
                temp = temp.prev;
            }
            System.out.println("-----------------------");
        }

        // Count total books
        void countBooks() {
            System.out.println("Total books in library: " + count);
        }

        private String formatBook(Node node) {
            return "ID: " + node.bookId + " | Title: " + node.bookTitle
                    + " | Author: " + node.author + " | Genre: " + node.genre
                    + " | Available: " + (node.available ? "Yes" : "No");
        }
    }

    public static void main(String[] args) {
        LibraryDoublyList library = new LibraryDoublyList();

        System.out.println("===== Library Management System =====\n");

        library.addAtEnd("The Alchemist", "Paulo Coelho", "Fiction", 301, true);
        library.addAtEnd("Clean Code", "Robert C. Martin", "Technology", 302, true);
        library.addAtEnd("1984", "George Orwell", "Dystopian", 303, false);
        library.addAtBeginning("Atomic Habits", "James Clear", "Self-Help", 300, true);
        library.addAtPosition("Deep Work", "Cal Newport", "Self-Help", 304, true, 3);

        library.displayForward();
        library.displayReverse();

        System.out.println("\n--- Search ---");
        library.searchByTitle("1984");
        library.searchByAuthor("Paulo Coelho");

        System.out.println("\n--- Update Availability ---");
        library.updateAvailability(303, true);

        System.out.println("\n--- Remove Book ---");
        library.removeByBookId(302);

        library.displayForward();
        library.countBooks();
    }
}
