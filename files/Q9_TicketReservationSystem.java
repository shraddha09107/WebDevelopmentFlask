// Q9. Circular Linked List: Online Ticket Reservation System

class Q9_TicketReservationSystem {

    static class Node {
        int ticketId;
        String customerName;
        String movieName;
        String seatNumber;
        String bookingTime;
        Node next;

        Node(int ticketId, String customerName, String movieName, String seatNumber, String bookingTime) {
            this.ticketId = ticketId;
            this.customerName = customerName;
            this.movieName = movieName;
            this.seatNumber = seatNumber;
            this.bookingTime = bookingTime;
            this.next = null;
        }
    }

    static class TicketCircularList {
        Node head;
        Node tail;
        int totalTickets;

        TicketCircularList() {
            head = null;
            tail = null;
            totalTickets = 0;
        }

        // Add new reservation at end
        void addTicket(int ticketId, String customerName, String movieName, String seatNumber, String bookingTime) {
            Node newNode = new Node(ticketId, customerName, movieName, seatNumber, bookingTime);
            if (head == null) {
                head = tail = newNode;
                newNode.next = head;
            } else {
                tail.next = newNode;
                tail = newNode;
                tail.next = head;
            }
            totalTickets++;
            System.out.println("Ticket booked: [ID: " + ticketId + "] " + customerName + " -> " + movieName + " (Seat: " + seatNumber + ")");
        }

        // Remove ticket by ticket ID
        void removeTicket(int ticketId) {
            if (head == null) {
                System.out.println("No tickets in the system.");
                return;
            }
            // Single node case
            if (head == tail && head.ticketId == ticketId) {
                System.out.println("Cancelled ticket: [ID: " + ticketId + "] " + head.customerName);
                head = tail = null;
                totalTickets--;
                return;
            }
            // Head node
            if (head.ticketId == ticketId) {
                System.out.println("Cancelled ticket: [ID: " + ticketId + "] " + head.customerName);
                head = head.next;
                tail.next = head;
                totalTickets--;
                return;
            }
            // Middle or tail
            Node temp = head;
            do {
                if (temp.next.ticketId == ticketId) {
                    System.out.println("Cancelled ticket: [ID: " + ticketId + "] " + temp.next.customerName);
                    if (temp.next == tail) tail = temp;
                    temp.next = temp.next.next;
                    totalTickets--;
                    return;
                }
                temp = temp.next;
            } while (temp != head);
            System.out.println("Ticket with ID " + ticketId + " not found.");
        }

        // Display all current tickets
        void displayTickets() {
            if (head == null) {
                System.out.println("No tickets booked.");
                return;
            }
            System.out.println("\n--- Current Ticket Reservations ---");
            Node temp = head;
            int index = 1;
            do {
                System.out.println(index++ + ". " + formatTicket(temp));
                temp = temp.next;
            } while (temp != head);
            System.out.println("-----------------------------------");
        }

        // Search by customer name
        void searchByCustomerName(String customerName) {
            if (head == null) {
                System.out.println("No tickets in the system.");
                return;
            }
            System.out.println("\n--- Tickets for Customer: " + customerName + " ---");
            boolean found = false;
            Node temp = head;
            do {
                if (temp.customerName.equalsIgnoreCase(customerName)) {
                    System.out.println("  -> " + formatTicket(temp));
                    found = true;
                }
                temp = temp.next;
            } while (temp != head);
            if (!found) System.out.println("No tickets found for customer: " + customerName);
        }

        // Search by movie name
        void searchByMovieName(String movieName) {
            if (head == null) {
                System.out.println("No tickets in the system.");
                return;
            }
            System.out.println("\n--- Tickets for Movie: " + movieName + " ---");
            boolean found = false;
            Node temp = head;
            do {
                if (temp.movieName.equalsIgnoreCase(movieName)) {
                    System.out.println("  -> " + formatTicket(temp));
                    found = true;
                }
                temp = temp.next;
            } while (temp != head);
            if (!found) System.out.println("No tickets found for movie: " + movieName);
        }

        // Calculate total booked tickets
        void displayTotalTickets() {
            System.out.println("Total Booked Tickets: " + totalTickets);
        }

        private String formatTicket(Node node) {
            return "TicketID: " + node.ticketId
                    + " | Customer: " + node.customerName
                    + " | Movie: " + node.movieName
                    + " | Seat: " + node.seatNumber
                    + " | Time: " + node.bookingTime;
        }
    }

    public static void main(String[] args) {
        TicketCircularList system = new TicketCircularList();

        System.out.println("===== Online Ticket Reservation System =====\n");

        // Add ticket reservations
        system.addTicket(1001, "Alice", "Inception", "A1", "10:00 AM");
        system.addTicket(1002, "Bob", "Inception", "A2", "10:05 AM");
        system.addTicket(1003, "Charlie", "Avengers: Endgame", "B3", "10:10 AM");
        system.addTicket(1004, "Diana", "Avengers: Endgame", "B4", "10:15 AM");
        system.addTicket(1005, "Alice", "The Dark Knight", "C5", "10:20 AM");

        system.displayTickets();
        system.displayTotalTickets();

        // Search
        System.out.println("\n--- Search ---");
        system.searchByCustomerName("Alice");
        system.searchByMovieName("Avengers: Endgame");

        // Cancel a ticket
        System.out.println("\n--- Cancel Ticket ---");
        system.removeTicket(1003);
        system.removeTicket(9999); // non-existent

        system.displayTickets();
        system.displayTotalTickets();
    }
}
