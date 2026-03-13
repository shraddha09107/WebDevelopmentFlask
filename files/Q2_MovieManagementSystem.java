// Q2. Doubly Linked List: Movie Management System

class Q2_MovieManagementSystem {

    static class Node {
        String title;
        String director;
        int year;
        double rating;
        Node next;
        Node prev;

        Node(String title, String director, int year, double rating) {
            this.title = title;
            this.director = director;
            this.year = year;
            this.rating = rating;
            this.next = null;
            this.prev = null;
        }
    }

    static class MovieDoublyLinkedList {
        Node head;
        Node tail;

        MovieDoublyLinkedList() {
            head = null;
            tail = null;
        }

        // Add at beginning
        void addAtBeginning(String title, String director, int year, double rating) {
            Node newNode = new Node(title, director, year, rating);
            if (head == null) {
                head = tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            System.out.println("Movie added at beginning: " + title);
        }

        // Add at end
        void addAtEnd(String title, String director, int year, double rating) {
            Node newNode = new Node(title, director, year, rating);
            if (tail == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            System.out.println("Movie added at end: " + title);
        }

        // Add at specific position (1-based)
        void addAtPosition(String title, String director, int year, double rating, int position) {
            if (position <= 1) {
                addAtBeginning(title, director, year, rating);
                return;
            }
            Node temp = head;
            for (int i = 1; i < position - 1 && temp != null; i++) {
                temp = temp.next;
            }
            if (temp == null || temp.next == null) {
                addAtEnd(title, director, year, rating);
                return;
            }
            Node newNode = new Node(title, director, year, rating);
            newNode.next = temp.next;
            newNode.prev = temp;
            if (temp.next != null) temp.next.prev = newNode;
            temp.next = newNode;
            System.out.println("Movie added at position " + position + ": " + title);
        }

        // Remove by title
        void removeByTitle(String title) {
            if (head == null) {
                System.out.println("List is empty.");
                return;
            }
            Node temp = head;
            while (temp != null) {
                if (temp.title.equalsIgnoreCase(title)) {
                    if (temp.prev != null) temp.prev.next = temp.next;
                    else head = temp.next;
                    if (temp.next != null) temp.next.prev = temp.prev;
                    else tail = temp.prev;
                    temp.next = null;
                    temp.prev = null;
                    System.out.println("Removed movie: " + title);
                    return;
                }
                temp = temp.next;
            }
            System.out.println("Movie '" + title + "' not found.");
        }

        // Search by director
        void searchByDirector(String director) {
            System.out.println("\n--- Movies by Director: " + director + " ---");
            boolean found = false;
            Node temp = head;
            while (temp != null) {
                if (temp.director.equalsIgnoreCase(director)) {
                    printMovie(temp);
                    found = true;
                }
                temp = temp.next;
            }
            if (!found) System.out.println("No movies found by director: " + director);
        }

        // Search by rating (exact match)
        void searchByRating(double rating) {
            System.out.println("\n--- Movies with Rating: " + rating + " ---");
            boolean found = false;
            Node temp = head;
            while (temp != null) {
                if (temp.rating == rating) {
                    printMovie(temp);
                    found = true;
                }
                temp = temp.next;
            }
            if (!found) System.out.println("No movies found with rating: " + rating);
        }

        // Display forward
        void displayForward() {
            if (head == null) {
                System.out.println("No movies found.");
                return;
            }
            System.out.println("\n--- Movies (Forward) ---");
            Node temp = head;
            int index = 1;
            while (temp != null) {
                System.out.println(index++ + ". " + formatMovie(temp));
                temp = temp.next;
            }
            System.out.println("------------------------");
        }

        // Display reverse
        void displayReverse() {
            if (tail == null) {
                System.out.println("No movies found.");
                return;
            }
            System.out.println("\n--- Movies (Reverse) ---");
            Node temp = tail;
            int index = 1;
            while (temp != null) {
                System.out.println(index++ + ". " + formatMovie(temp));
                temp = temp.prev;
            }
            System.out.println("------------------------");
        }

        // Update rating by title
        void updateRating(String title, double newRating) {
            Node temp = head;
            while (temp != null) {
                if (temp.title.equalsIgnoreCase(title)) {
                    System.out.println("Updated rating for '" + title + "': " + temp.rating + " -> " + newRating);
                    temp.rating = newRating;
                    return;
                }
                temp = temp.next;
            }
            System.out.println("Movie '" + title + "' not found.");
        }

        private void printMovie(Node node) {
            System.out.println("  -> " + formatMovie(node));
        }

        private String formatMovie(Node node) {
            return "Title: " + node.title + " | Director: " + node.director
                    + " | Year: " + node.year + " | Rating: " + node.rating;
        }
    }

    public static void main(String[] args) {
        MovieDoublyLinkedList list = new MovieDoublyLinkedList();

        System.out.println("===== Movie Management System =====\n");

        list.addAtEnd("Inception", "Christopher Nolan", 2010, 8.8);
        list.addAtEnd("The Dark Knight", "Christopher Nolan", 2008, 9.0);
        list.addAtEnd("Interstellar", "Christopher Nolan", 2014, 8.6);
        list.addAtBeginning("Avengers: Endgame", "Russo Brothers", 2019, 8.4);
        list.addAtPosition("Parasite", "Bong Joon-ho", 2019, 8.5, 3);

        list.displayForward();
        list.displayReverse();

        System.out.println("\n--- Search ---");
        list.searchByDirector("Christopher Nolan");
        list.searchByRating(8.8);

        System.out.println("\n--- Update Rating ---");
        list.updateRating("Inception", 9.1);

        System.out.println("\n--- Remove Movie ---");
        list.removeByTitle("Interstellar");

        list.displayForward();
    }
}
