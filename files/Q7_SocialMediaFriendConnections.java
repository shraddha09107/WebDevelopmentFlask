// Q7. Singly Linked List: Social Media Friend Connections

import java.util.ArrayList;
import java.util.List;

class Q7_SocialMediaFriendConnections {

    // Friend ID node (nested singly linked list for friend IDs)
    static class FriendNode {
        int friendId;
        FriendNode next;

        FriendNode(int friendId) {
            this.friendId = friendId;
            this.next = null;
        }
    }

    // User node
    static class UserNode {
        int userId;
        String name;
        int age;
        FriendNode friendListHead; // head of friend ID linked list
        UserNode next;

        UserNode(int userId, String name, int age) {
            this.userId = userId;
            this.name = name;
            this.age = age;
            this.friendListHead = null;
            this.next = null;
        }

        // Add friend ID to this user's list
        void addFriend(int friendId) {
            FriendNode newFriend = new FriendNode(friendId);
            if (friendListHead == null) {
                friendListHead = newFriend;
            } else {
                FriendNode temp = friendListHead;
                while (temp.next != null) temp = temp.next;
                temp.next = newFriend;
            }
        }

        // Remove friend ID from this user's list
        boolean removeFriend(int friendId) {
            if (friendListHead == null) return false;
            if (friendListHead.friendId == friendId) {
                friendListHead = friendListHead.next;
                return true;
            }
            FriendNode temp = friendListHead;
            while (temp.next != null && temp.next.friendId != friendId) temp = temp.next;
            if (temp.next == null) return false;
            temp.next = temp.next.next;
            return true;
        }

        // Count friends
        int countFriends() {
            int count = 0;
            FriendNode temp = friendListHead;
            while (temp != null) { count++; temp = temp.next; }
            return count;
        }

        // Get all friend IDs as list
        List<Integer> getFriendIds() {
            List<Integer> ids = new ArrayList<>();
            FriendNode temp = friendListHead;
            while (temp != null) { ids.add(temp.friendId); temp = temp.next; }
            return ids;
        }
    }

    // Main social network using singly linked list of users
    static class SocialNetwork {
        UserNode head;

        SocialNetwork() {
            head = null;
        }

        // Add a user
        void addUser(int userId, String name, int age) {
            if (findUser(userId) != null) {
                System.out.println("User with ID " + userId + " already exists.");
                return;
            }
            UserNode newUser = new UserNode(userId, name, age);
            if (head == null) {
                head = newUser;
            } else {
                UserNode temp = head;
                while (temp.next != null) temp = temp.next;
                temp.next = newUser;
            }
            System.out.println("User added: " + name + " (ID: " + userId + ")");
        }

        // Find user by ID
        UserNode findUser(int userId) {
            UserNode temp = head;
            while (temp != null) {
                if (temp.userId == userId) return temp;
                temp = temp.next;
            }
            return null;
        }

        // Find user by name
        void searchByName(String name) {
            System.out.println("\n--- Search by Name: " + name + " ---");
            boolean found = false;
            UserNode temp = head;
            while (temp != null) {
                if (temp.name.equalsIgnoreCase(name)) {
                    System.out.println("Found -> " + formatUser(temp));
                    found = true;
                }
                temp = temp.next;
            }
            if (!found) System.out.println("No user found with name: " + name);
        }

        // Search by user ID
        void searchByUserId(int userId) {
            System.out.println("\n--- Search by User ID: " + userId + " ---");
            UserNode user = findUser(userId);
            if (user != null) System.out.println("Found -> " + formatUser(user));
            else System.out.println("User with ID " + userId + " not found.");
        }

        // Add friend connection (bidirectional)
        void addFriendConnection(int userId1, int userId2) {
            UserNode user1 = findUser(userId1);
            UserNode user2 = findUser(userId2);
            if (user1 == null || user2 == null) {
                System.out.println("One or both users not found.");
                return;
            }
            user1.addFriend(userId2);
            user2.addFriend(userId1);
            System.out.println("Friend connection added: " + user1.name + " <-> " + user2.name);
        }

        // Remove friend connection (bidirectional)
        void removeFriendConnection(int userId1, int userId2) {
            UserNode user1 = findUser(userId1);
            UserNode user2 = findUser(userId2);
            if (user1 == null || user2 == null) {
                System.out.println("One or both users not found.");
                return;
            }
            boolean removed1 = user1.removeFriend(userId2);
            boolean removed2 = user2.removeFriend(userId1);
            if (removed1 && removed2) {
                System.out.println("Friend connection removed: " + user1.name + " <-> " + user2.name);
            } else {
                System.out.println("Connection between these users does not exist.");
            }
        }

        // Find mutual friends between two users
        void findMutualFriends(int userId1, int userId2) {
            UserNode user1 = findUser(userId1);
            UserNode user2 = findUser(userId2);
            if (user1 == null || user2 == null) {
                System.out.println("One or both users not found.");
                return;
            }
            List<Integer> friends1 = user1.getFriendIds();
            List<Integer> friends2 = user2.getFriendIds();
            System.out.println("\n--- Mutual Friends of " + user1.name + " and " + user2.name + " ---");
            boolean found = false;
            for (int id : friends1) {
                if (friends2.contains(id)) {
                    UserNode mutual = findUser(id);
                    if (mutual != null) {
                        System.out.println("  -> " + mutual.name + " (ID: " + id + ")");
                        found = true;
                    }
                }
            }
            if (!found) System.out.println("No mutual friends found.");
        }

        // Display all friends of a user
        void displayFriends(int userId) {
            UserNode user = findUser(userId);
            if (user == null) {
                System.out.println("User with ID " + userId + " not found.");
                return;
            }
            System.out.println("\n--- Friends of " + user.name + " ---");
            if (user.friendListHead == null) {
                System.out.println("No friends yet.");
                return;
            }
            FriendNode temp = user.friendListHead;
            int index = 1;
            while (temp != null) {
                UserNode friend = findUser(temp.friendId);
                System.out.println(index++ + ". " + (friend != null ? friend.name : "Unknown") + " (ID: " + temp.friendId + ")");
                temp = temp.next;
            }
        }

        // Count friends for each user
        void countFriendsAll() {
            System.out.println("\n--- Friend Count per User ---");
            UserNode temp = head;
            while (temp != null) {
                System.out.println(temp.name + " (ID: " + temp.userId + "): " + temp.countFriends() + " friends");
                temp = temp.next;
            }
        }

        private String formatUser(UserNode user) {
            return "ID: " + user.userId + " | Name: " + user.name + " | Age: " + user.age
                    + " | Friends: " + user.countFriends();
        }
    }

    public static void main(String[] args) {
        SocialNetwork network = new SocialNetwork();

        System.out.println("===== Social Media Friend Connections =====\n");

        // Add users
        network.addUser(1, "Alice", 22);
        network.addUser(2, "Bob", 24);
        network.addUser(3, "Charlie", 21);
        network.addUser(4, "Diana", 23);
        network.addUser(5, "Eve", 25);

        // Add connections
        System.out.println("\n--- Adding Connections ---");
        network.addFriendConnection(1, 2); // Alice <-> Bob
        network.addFriendConnection(1, 3); // Alice <-> Charlie
        network.addFriendConnection(2, 3); // Bob <-> Charlie
        network.addFriendConnection(3, 4); // Charlie <-> Diana
        network.addFriendConnection(2, 4); // Bob <-> Diana

        // Display friends
        network.displayFriends(1);
        network.displayFriends(2);

        // Mutual friends
        network.findMutualFriends(1, 4); // Alice and Diana

        // Search
        network.searchByName("Bob");
        network.searchByUserId(3);

        // Count
        network.countFriendsAll();

        // Remove connection
        System.out.println("\n--- Remove Connection ---");
        network.removeFriendConnection(1, 3);
        network.displayFriends(1);
    }
}
