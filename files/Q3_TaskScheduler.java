// Q3. Circular Linked List: Task Scheduler

class Q3_TaskScheduler {

    static class Node {
        int taskId;
        String taskName;
        int priority;
        String dueDate;
        Node next;

        Node(int taskId, String taskName, int priority, String dueDate) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.priority = priority;
            this.dueDate = dueDate;
            this.next = null;
        }
    }

    static class CircularTaskList {
        Node head;
        Node tail;
        Node current; // pointer to current task

        CircularTaskList() {
            head = null;
            tail = null;
            current = null;
        }

        // Add at beginning
        void addAtBeginning(int taskId, String taskName, int priority, String dueDate) {
            Node newNode = new Node(taskId, taskName, priority, dueDate);
            if (head == null) {
                head = tail = newNode;
                newNode.next = head;
                current = head;
            } else {
                newNode.next = head;
                head = newNode;
                tail.next = head;
            }
            System.out.println("Task added at beginning: " + taskName);
        }

        // Add at end
        void addAtEnd(int taskId, String taskName, int priority, String dueDate) {
            Node newNode = new Node(taskId, taskName, priority, dueDate);
            if (head == null) {
                head = tail = newNode;
                newNode.next = head;
                current = head;
            } else {
                tail.next = newNode;
                tail = newNode;
                tail.next = head;
            }
            System.out.println("Task added at end: " + taskName);
        }

        // Add at specific position (1-based)
        void addAtPosition(int taskId, String taskName, int priority, String dueDate, int position) {
            if (head == null || position <= 1) {
                addAtBeginning(taskId, taskName, priority, dueDate);
                return;
            }
            Node newNode = new Node(taskId, taskName, priority, dueDate);
            Node temp = head;
            for (int i = 1; i < position - 1; i++) {
                temp = temp.next;
                if (temp == head) break; // completed one cycle
            }
            if (temp.next == head) {
                // inserting at end
                temp.next = newNode;
                tail = newNode;
                tail.next = head;
            } else {
                newNode.next = temp.next;
                temp.next = newNode;
            }
            System.out.println("Task added at position " + position + ": " + taskName);
        }

        // Remove by task ID
        void removeByTaskId(int taskId) {
            if (head == null) {
                System.out.println("List is empty.");
                return;
            }
            // Single node
            if (head == tail && head.taskId == taskId) {
                System.out.println("Removed task: " + head.taskName);
                head = tail = current = null;
                return;
            }
            // Head node
            if (head.taskId == taskId) {
                System.out.println("Removed task: " + head.taskName);
                if (current == head) current = head.next;
                head = head.next;
                tail.next = head;
                return;
            }
            Node temp = head;
            do {
                if (temp.next.taskId == taskId) {
                    System.out.println("Removed task: " + temp.next.taskName);
                    if (current == temp.next) current = temp.next.next;
                    if (temp.next == tail) tail = temp;
                    temp.next = temp.next.next;
                    return;
                }
                temp = temp.next;
            } while (temp != head);
            System.out.println("Task with ID " + taskId + " not found.");
        }

        // View current task and move to next
        void viewCurrentAndMoveNext() {
            if (current == null) {
                System.out.println("No tasks available.");
                return;
            }
            System.out.println("Current Task -> " + formatTask(current));
            current = current.next;
            System.out.println("Moved to next task: " + current.taskName);
        }

        // Display all tasks
        void displayAll() {
            if (head == null) {
                System.out.println("No tasks in the scheduler.");
                return;
            }
            System.out.println("\n--- Task List (Circular) ---");
            Node temp = head;
            int index = 1;
            do {
                System.out.println(index++ + ". " + formatTask(temp));
                temp = temp.next;
            } while (temp != head);
            System.out.println("----------------------------");
        }

        // Search by priority
        void searchByPriority(int priority) {
            if (head == null) {
                System.out.println("List is empty.");
                return;
            }
            System.out.println("\n--- Tasks with Priority " + priority + " ---");
            boolean found = false;
            Node temp = head;
            do {
                if (temp.priority == priority) {
                    System.out.println("  -> " + formatTask(temp));
                    found = true;
                }
                temp = temp.next;
            } while (temp != head);
            if (!found) System.out.println("No tasks found with priority: " + priority);
        }

        private String formatTask(Node node) {
            return "ID: " + node.taskId + " | Name: " + node.taskName
                    + " | Priority: " + node.priority + " | Due: " + node.dueDate;
        }
    }

    public static void main(String[] args) {
        CircularTaskList scheduler = new CircularTaskList();

        System.out.println("===== Task Scheduler (Circular Linked List) =====\n");

        scheduler.addAtEnd(1, "Design UI", 2, "2024-12-01");
        scheduler.addAtEnd(2, "Write Tests", 1, "2024-12-05");
        scheduler.addAtEnd(3, "Deploy App", 3, "2024-12-10");
        scheduler.addAtBeginning(0, "Requirement Analysis", 1, "2024-11-28");
        scheduler.addAtPosition(4, "Code Review", 2, "2024-12-03", 3);

        scheduler.displayAll();

        System.out.println("\n--- Current Task & Move Next ---");
        scheduler.viewCurrentAndMoveNext();
        scheduler.viewCurrentAndMoveNext();

        System.out.println("\n--- Search by Priority ---");
        scheduler.searchByPriority(1);

        System.out.println("\n--- Remove Task ---");
        scheduler.removeByTaskId(2);

        scheduler.displayAll();
    }
}
