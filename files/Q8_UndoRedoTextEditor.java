// Q8. Doubly Linked List: Undo/Redo Functionality for Text Editor

class Q8_UndoRedoTextEditor {

    static class Node {
        String textState;
        Node next;
        Node prev;

        Node(String textState) {
            this.textState = textState;
            this.next = null;
            this.prev = null;
        }
    }

    static class TextEditor {
        Node current;   // pointer to current state
        int historySize;
        int maxHistory;
        int undoCount;  // how many nodes exist before current (for size management)

        TextEditor(int maxHistory) {
            this.current = null;
            this.historySize = 0;
            this.maxHistory = maxHistory;
            this.undoCount = 0;
        }

        // Add new text state (called every time user types / performs action)
        void addState(String newText) {
            Node newNode = new Node(newText);

            // If there are redo states ahead, remove them
            if (current != null && current.next != null) {
                current.next = null; // drop redo history
            }

            if (current == null) {
                current = newNode;
            } else {
                newNode.prev = current;
                current.next = newNode;
                current = newNode;
                historySize++;
            }

            // Enforce max history: remove oldest node if over limit
            if (historySize >= maxHistory) {
                trimHistory();
            }

            System.out.println("State saved: \"" + newText + "\"");
        }

        // Trim oldest state to maintain max history
        private void trimHistory() {
            Node temp = current;
            while (temp.prev != null && temp.prev.prev != null) temp = temp.prev;
            if (temp.prev != null) {
                temp.prev = null;
                historySize = maxHistory - 1;
            }
        }

        // Undo - revert to previous state
        void undo() {
            if (current == null) {
                System.out.println("Nothing to undo.");
                return;
            }
            if (current.prev == null) {
                System.out.println("No more undo history.");
                return;
            }
            current = current.prev;
            System.out.println("Undo -> Current state: \"" + current.textState + "\"");
        }

        // Redo - revert to next state after undo
        void redo() {
            if (current == null) {
                System.out.println("Nothing to redo.");
                return;
            }
            if (current.next == null) {
                System.out.println("No redo state available.");
                return;
            }
            current = current.next;
            System.out.println("Redo -> Current state: \"" + current.textState + "\"");
        }

        // Display current state
        void displayCurrentState() {
            if (current == null) {
                System.out.println("Editor is empty.");
            } else {
                System.out.println("Current State: \"" + current.textState + "\"");
            }
        }

        // Display full history
        void displayHistory() {
            if (current == null) {
                System.out.println("No history available.");
                return;
            }
            // Go to oldest state
            Node temp = current;
            while (temp.prev != null) temp = temp.prev;

            System.out.println("\n--- Edit History ---");
            int index = 1;
            while (temp != null) {
                String marker = (temp == current) ? " <<< CURRENT" : "";
                System.out.println(index++ + ". \"" + temp.textState + "\"" + marker);
                temp = temp.next;
            }
            System.out.println("--------------------");
        }
    }

    public static void main(String[] args) {
        // Create editor with max history of 10 states
        TextEditor editor = new TextEditor(10);

        System.out.println("===== Undo/Redo Text Editor =====\n");

        // Simulate typing
        editor.addState("Hello");
        editor.addState("Hello World");
        editor.addState("Hello World!");
        editor.addState("Hello World! How");
        editor.addState("Hello World! How are");
        editor.addState("Hello World! How are you?");

        editor.displayHistory();

        // Undo operations
        System.out.println("\n--- Undo Operations ---");
        editor.undo();
        editor.undo();
        editor.undo();
        editor.displayCurrentState();

        // Redo operations
        System.out.println("\n--- Redo Operations ---");
        editor.redo();
        editor.redo();
        editor.displayCurrentState();

        // Add new state after undo (should drop redo history)
        System.out.println("\n--- New State after partial undo (drops redo) ---");
        editor.addState("Hello World! Hello Again");
        editor.displayHistory();

        // Try redo after new state was added
        System.out.println("\n--- Try redo (should be unavailable) ---");
        editor.redo();

        // Undo to beginning
        System.out.println("\n--- Undo to start ---");
        editor.undo();
        editor.undo();
        editor.undo();
        editor.undo();
        editor.undo(); // should say no more undo
    }
}
