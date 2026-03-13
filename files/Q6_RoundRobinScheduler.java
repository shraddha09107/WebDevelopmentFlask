// Q6. Circular Linked List: Round Robin CPU Scheduling Algorithm

class Q6_RoundRobinScheduler {

    static class Node {
        int processId;
        int burstTime;
        int remainingTime;
        int priority;
        int waitingTime;
        int turnaroundTime;
        Node next;

        Node(int processId, int burstTime, int priority) {
            this.processId = processId;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
            this.priority = priority;
            this.waitingTime = 0;
            this.turnaroundTime = 0;
            this.next = null;
        }
    }

    static class RoundRobinList {
        Node head;
        Node tail;
        int size;

        RoundRobinList() {
            head = null;
            tail = null;
            size = 0;
        }

        // Add process at end of circular list
        void addProcess(int processId, int burstTime, int priority) {
            Node newNode = new Node(processId, burstTime, priority);
            if (head == null) {
                head = tail = newNode;
                newNode.next = head;
            } else {
                tail.next = newNode;
                tail = newNode;
                tail.next = head;
            }
            size++;
            System.out.println("Process P" + processId + " added | Burst: " + burstTime + " | Priority: " + priority);
        }

        // Remove process by process ID
        void removeProcess(int processId) {
            if (head == null) {
                System.out.println("No processes in queue.");
                return;
            }
            if (head == tail && head.processId == processId) {
                System.out.println("Removed process: P" + processId);
                head = tail = null;
                size--;
                return;
            }
            if (head.processId == processId) {
                System.out.println("Removed process: P" + processId);
                head = head.next;
                tail.next = head;
                size--;
                return;
            }
            Node temp = head;
            do {
                if (temp.next.processId == processId) {
                    System.out.println("Removed process: P" + temp.next.processId);
                    if (temp.next == tail) tail = temp;
                    temp.next = temp.next.next;
                    size--;
                    return;
                }
                temp = temp.next;
            } while (temp != head);
            System.out.println("Process P" + processId + " not found.");
        }

        // Display current process queue
        void displayProcesses() {
            if (head == null) {
                System.out.println("No processes in queue.");
                return;
            }
            System.out.println("\n--- Current Process Queue ---");
            Node temp = head;
            do {
                System.out.println("  P" + temp.processId
                        + " | Burst: " + temp.burstTime
                        + " | Remaining: " + temp.remainingTime
                        + " | Priority: " + temp.priority);
                temp = temp.next;
            } while (temp != head);
            System.out.println("-----------------------------");
        }

        // Simulate Round Robin scheduling
        void simulate(int timeQuantum) {
            if (head == null) {
                System.out.println("No processes to schedule.");
                return;
            }
            System.out.println("\n===== Round Robin Simulation (Time Quantum = " + timeQuantum + ") =====");

            // Store original process info for final calculation
            int totalProcesses = size;
            int[] burstTimes = new int[totalProcesses];
            int[] pids = new int[totalProcesses];
            Node temp = head;
            for (int i = 0; i < totalProcesses; i++) {
                burstTimes[i] = temp.burstTime;
                pids[i] = temp.processId;
                temp = temp.next;
            }

            int currentTime = 0;
            int completedCount = 0;

            // We work on a local copy concept via remainingTime
            while (completedCount < totalProcesses) {
                boolean anyProgress = false;
                Node curr = head;
                do {
                    if (curr.remainingTime > 0) {
                        anyProgress = true;
                        int execTime = Math.min(timeQuantum, curr.remainingTime);
                        System.out.println("Time " + currentTime + ": Executing P" + curr.processId
                                + " for " + execTime + " units.");
                        curr.remainingTime -= execTime;
                        currentTime += execTime;

                        if (curr.remainingTime == 0) {
                            curr.turnaroundTime = currentTime;
                            curr.waitingTime = curr.turnaroundTime - curr.burstTime;
                            System.out.println("  >> P" + curr.processId + " completed at time " + currentTime);
                            completedCount++;
                        }
                    }
                    curr = curr.next;
                } while (curr != head);

                if (!anyProgress) break;
            }

            // Display stats
            System.out.println("\n--- Process Statistics ---");
            System.out.printf("%-10s %-12s %-15s %-15s%n", "Process", "Burst Time", "Waiting Time", "Turnaround Time");
            double totalWait = 0, totalTAT = 0;
            temp = head;
            for (int i = 0; i < totalProcesses; i++) {
                System.out.printf("%-10s %-12d %-15d %-15d%n",
                        "P" + temp.processId, temp.burstTime, temp.waitingTime, temp.turnaroundTime);
                totalWait += temp.waitingTime;
                totalTAT += temp.turnaroundTime;
                temp = temp.next;
            }
            System.out.printf("%nAverage Waiting Time    : %.2f%n", totalWait / totalProcesses);
            System.out.printf("Average Turnaround Time : %.2f%n", totalTAT / totalProcesses);
        }
    }

    public static void main(String[] args) {
        RoundRobinList scheduler = new RoundRobinList();

        System.out.println("===== Round Robin CPU Scheduling =====\n");

        // Add processes
        scheduler.addProcess(1, 10, 1);
        scheduler.addProcess(2, 5, 2);
        scheduler.addProcess(3, 8, 1);
        scheduler.addProcess(4, 3, 3);

        scheduler.displayProcesses();

        // Simulate with time quantum = 3
        scheduler.simulate(3);
    }
}
