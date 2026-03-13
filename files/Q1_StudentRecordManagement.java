// Q1. Singly Linked List: Student Record Management

class Q1_StudentRecordManagement {

    // Node class
    static class Node {
        int rollNumber;
        String name;
        int age;
        String grade;
        Node next;

        Node(int rollNumber, String name, int age, String grade) {
            this.rollNumber = rollNumber;
            this.name = name;
            this.age = age;
            this.grade = grade;
            this.next = null;
        }
    }

    // Linked List class
    static class StudentLinkedList {
        Node head;

        StudentLinkedList() {
            head = null;
        }

        // Add at beginning
        void addAtBeginning(int rollNumber, String name, int age, String grade) {
            Node newNode = new Node(rollNumber, name, age, grade);
            newNode.next = head;
            head = newNode;
            System.out.println("Student added at beginning: " + name);
        }

        // Add at end
        void addAtEnd(int rollNumber, String name, int age, String grade) {
            Node newNode = new Node(rollNumber, name, age, grade);
            if (head == null) {
                head = newNode;
            } else {
                Node temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = newNode;
            }
            System.out.println("Student added at end: " + name);
        }

        // Add at specific position (1-based)
        void addAtPosition(int rollNumber, String name, int age, String grade, int position) {
            if (position <= 1) {
                addAtBeginning(rollNumber, name, age, grade);
                return;
            }
            Node newNode = new Node(rollNumber, name, age, grade);
            Node temp = head;
            for (int i = 1; i < position - 1 && temp != null; i++) {
                temp = temp.next;
            }
            if (temp == null) {
                System.out.println("Position out of range. Adding at end.");
                addAtEnd(rollNumber, name, age, grade);
                return;
            }
            newNode.next = temp.next;
            temp.next = newNode;
            System.out.println("Student added at position " + position + ": " + name);
        }

        // Delete by roll number
        void deleteByRollNumber(int rollNumber) {
            if (head == null) {
                System.out.println("List is empty.");
                return;
            }
            if (head.rollNumber == rollNumber) {
                System.out.println("Deleted student: " + head.name);
                head = head.next;
                return;
            }
            Node temp = head;
            while (temp.next != null && temp.next.rollNumber != rollNumber) {
                temp = temp.next;
            }
            if (temp.next == null) {
                System.out.println("Student with Roll Number " + rollNumber + " not found.");
            } else {
                System.out.println("Deleted student: " + temp.next.name);
                temp.next = temp.next.next;
            }
        }

        // Search by roll number
        void searchByRollNumber(int rollNumber) {
            if (head == null) {
                System.out.println("List is empty.");
                return;
            }
            Node temp = head;
            while (temp != null) {
                if (temp.rollNumber == rollNumber) {
                    System.out.println("Student Found -> Roll: " + temp.rollNumber
                            + ", Name: " + temp.name + ", Age: " + temp.age + ", Grade: " + temp.grade);
                    return;
                }
                temp = temp.next;
            }
            System.out.println("Student with Roll Number " + rollNumber + " not found.");
        }

        // Display all records
        void displayAll() {
            if (head == null) {
                System.out.println("No student records found.");
                return;
            }
            System.out.println("\n--- Student Records ---");
            Node temp = head;
            int index = 1;
            while (temp != null) {
                System.out.println(index++ + ". Roll: " + temp.rollNumber
                        + " | Name: " + temp.name
                        + " | Age: " + temp.age
                        + " | Grade: " + temp.grade);
                temp = temp.next;
            }
            System.out.println("-----------------------");
        }

        // Update grade by roll number
        void updateGrade(int rollNumber, String newGrade) {
            if (head == null) {
                System.out.println("List is empty.");
                return;
            }
            Node temp = head;
            while (temp != null) {
                if (temp.rollNumber == rollNumber) {
                    System.out.println("Updated grade for " + temp.name + ": " + temp.grade + " -> " + newGrade);
                    temp.grade = newGrade;
                    return;
                }
                temp = temp.next;
            }
            System.out.println("Student with Roll Number " + rollNumber + " not found.");
        }
    }

    // Main method to test
    public static void main(String[] args) {
        StudentLinkedList list = new StudentLinkedList();

        System.out.println("===== Student Record Management =====\n");

        // Add students
        list.addAtEnd(101, "Alice", 20, "A");
        list.addAtEnd(102, "Bob", 21, "B");
        list.addAtEnd(103, "Charlie", 22, "A");
        list.addAtBeginning(100, "Zara", 19, "A+");
        list.addAtPosition(104, "David", 20, "C", 3);

        // Display all
        list.displayAll();

        // Search
        System.out.println("\n--- Search ---");
        list.searchByRollNumber(102);
        list.searchByRollNumber(999);

        // Update grade
        System.out.println("\n--- Update Grade ---");
        list.updateGrade(102, "A");

        // Delete
        System.out.println("\n--- Delete ---");
        list.deleteByRollNumber(103);
        list.deleteByRollNumber(999);

        // Display after modifications
        list.displayAll();
    }
}
