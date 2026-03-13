// Q4. Singly Linked List: Inventory Management System

class Q4_InventoryManagementSystem {

    static class Node {
        String itemName;
        int itemId;
        int quantity;
        double price;
        Node next;

        Node(String itemName, int itemId, int quantity, double price) {
            this.itemName = itemName;
            this.itemId = itemId;
            this.quantity = quantity;
            this.price = price;
            this.next = null;
        }
    }

    static class InventoryList {
        Node head;

        InventoryList() {
            head = null;
        }

        // Add at beginning
        void addAtBeginning(String itemName, int itemId, int quantity, double price) {
            Node newNode = new Node(itemName, itemId, quantity, price);
            newNode.next = head;
            head = newNode;
            System.out.println("Item added at beginning: " + itemName);
        }

        // Add at end
        void addAtEnd(String itemName, int itemId, int quantity, double price) {
            Node newNode = new Node(itemName, itemId, quantity, price);
            if (head == null) {
                head = newNode;
            } else {
                Node temp = head;
                while (temp.next != null) temp = temp.next;
                temp.next = newNode;
            }
            System.out.println("Item added at end: " + itemName);
        }

        // Add at specific position (1-based)
        void addAtPosition(String itemName, int itemId, int quantity, double price, int position) {
            if (position <= 1) {
                addAtBeginning(itemName, itemId, quantity, price);
                return;
            }
            Node newNode = new Node(itemName, itemId, quantity, price);
            Node temp = head;
            for (int i = 1; i < position - 1 && temp != null; i++) temp = temp.next;
            if (temp == null) {
                addAtEnd(itemName, itemId, quantity, price);
                return;
            }
            newNode.next = temp.next;
            temp.next = newNode;
            System.out.println("Item added at position " + position + ": " + itemName);
        }

        // Remove by item ID
        void removeByItemId(int itemId) {
            if (head == null) {
                System.out.println("Inventory is empty.");
                return;
            }
            if (head.itemId == itemId) {
                System.out.println("Removed item: " + head.itemName);
                head = head.next;
                return;
            }
            Node temp = head;
            while (temp.next != null && temp.next.itemId != itemId) temp = temp.next;
            if (temp.next == null) {
                System.out.println("Item with ID " + itemId + " not found.");
            } else {
                System.out.println("Removed item: " + temp.next.itemName);
                temp.next = temp.next.next;
            }
        }

        // Update quantity by item ID
        void updateQuantity(int itemId, int newQuantity) {
            Node temp = head;
            while (temp != null) {
                if (temp.itemId == itemId) {
                    System.out.println("Updated quantity for '" + temp.itemName + "': " + temp.quantity + " -> " + newQuantity);
                    temp.quantity = newQuantity;
                    return;
                }
                temp = temp.next;
            }
            System.out.println("Item with ID " + itemId + " not found.");
        }

        // Search by item ID
        void searchByItemId(int itemId) {
            Node temp = head;
            while (temp != null) {
                if (temp.itemId == itemId) {
                    System.out.println("Found -> " + formatItem(temp));
                    return;
                }
                temp = temp.next;
            }
            System.out.println("Item with ID " + itemId + " not found.");
        }

        // Search by item name
        void searchByItemName(String itemName) {
            Node temp = head;
            boolean found = false;
            while (temp != null) {
                if (temp.itemName.equalsIgnoreCase(itemName)) {
                    System.out.println("Found -> " + formatItem(temp));
                    found = true;
                }
                temp = temp.next;
            }
            if (!found) System.out.println("Item '" + itemName + "' not found.");
        }

        // Calculate total inventory value
        void calculateTotalValue() {
            double total = 0;
            Node temp = head;
            while (temp != null) {
                total += temp.price * temp.quantity;
                temp = temp.next;
            }
            System.out.printf("Total Inventory Value: Rs. %.2f%n", total);
        }

        // Display all items
        void displayAll() {
            if (head == null) {
                System.out.println("Inventory is empty.");
                return;
            }
            System.out.println("\n--- Inventory ---");
            Node temp = head;
            int index = 1;
            while (temp != null) {
                System.out.println(index++ + ". " + formatItem(temp));
                temp = temp.next;
            }
            System.out.println("-----------------");
        }

        // Sort by item name (merge sort)
        void sortByName(boolean ascending) {
            head = mergeSort(head, true, ascending);
            System.out.println("Inventory sorted by Name (" + (ascending ? "Ascending" : "Descending") + ").");
        }

        // Sort by price (merge sort)
        void sortByPrice(boolean ascending) {
            head = mergeSort(head, false, ascending);
            System.out.println("Inventory sorted by Price (" + (ascending ? "Ascending" : "Descending") + ").");
        }

        // Merge sort
        private Node mergeSort(Node node, boolean byName, boolean ascending) {
            if (node == null || node.next == null) return node;
            Node mid = getMiddle(node);
            Node secondHalf = mid.next;
            mid.next = null;
            Node left = mergeSort(node, byName, ascending);
            Node right = mergeSort(secondHalf, byName, ascending);
            return merge(left, right, byName, ascending);
        }

        private Node merge(Node left, Node right, boolean byName, boolean ascending) {
            if (left == null) return right;
            if (right == null) return left;
            boolean condition;
            if (byName) {
                condition = ascending
                        ? left.itemName.compareToIgnoreCase(right.itemName) <= 0
                        : left.itemName.compareToIgnoreCase(right.itemName) >= 0;
            } else {
                condition = ascending ? left.price <= right.price : left.price >= right.price;
            }
            if (condition) {
                left.next = merge(left.next, right, byName, ascending);
                return left;
            } else {
                right.next = merge(left, right.next, byName, ascending);
                return right;
            }
        }

        private Node getMiddle(Node node) {
            if (node == null) return node;
            Node slow = node, fast = node.next;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            return slow;
        }

        private String formatItem(Node node) {
            return "ID: " + node.itemId + " | Name: " + node.itemName
                    + " | Qty: " + node.quantity + " | Price: Rs." + node.price;
        }
    }

    public static void main(String[] args) {
        InventoryList inventory = new InventoryList();

        System.out.println("===== Inventory Management System =====\n");

        inventory.addAtEnd("Laptop", 201, 10, 55000.0);
        inventory.addAtEnd("Mouse", 202, 50, 500.0);
        inventory.addAtEnd("Keyboard", 203, 30, 800.0);
        inventory.addAtBeginning("Monitor", 200, 15, 12000.0);
        inventory.addAtPosition("Headphones", 204, 20, 1500.0, 3);

        inventory.displayAll();

        System.out.println("\n--- Search ---");
        inventory.searchByItemId(202);
        inventory.searchByItemName("Laptop");

        System.out.println("\n--- Update Quantity ---");
        inventory.updateQuantity(203, 45);

        System.out.println("\n--- Total Value ---");
        inventory.calculateTotalValue();

        System.out.println("\n--- Sort by Name (Ascending) ---");
        inventory.sortByName(true);
        inventory.displayAll();

        System.out.println("\n--- Sort by Price (Descending) ---");
        inventory.sortByPrice(false);
        inventory.displayAll();

        System.out.println("\n--- Remove Item ---");
        inventory.removeByItemId(202);
        inventory.displayAll();
    }
}
