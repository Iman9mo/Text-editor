package midterm_project;

class Node {

    String data;
    Node next;

    Node(String d) {
        data = d;
        next = null;
    }
}

public class Lines {

    Node head;
    int numberOfLines = 0;

    public void insert(String data) {
        Node new_node = new Node(data);
        new_node.next = null;

        if (head == null || head.data == null) {
            head = new_node;
            numberOfLines = 1;
        } else {
            Node last = head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = new_node;
            numberOfLines++;
        }
    }

    public void printList() {
        Node currNode = this.head;
        while (currNode != null) {
            System.out.println(currNode.data);
            currNode = currNode.next;
        }
    }

    public void show(int n) {
        Node x = head;
        for (int i = 0; i < n && x != null; i++) {
            System.out.println(x.data);
            x = x.next;
        }
    }

    public void deleteAtPosition(int index) {
        Node currNode = head, prev = null;
        if (index == 0 && currNode != null) {
            head = currNode.next;
            System.out.println((index + 1) + " position element deleted");
            return;
        }
        int counter = 0;
        while (currNode != null) {
            if (counter == index) {
                prev.next = currNode.next;
                System.out.println((index + 1) + " position element deleted");
                break;
            } else {
                prev = currNode;
                currNode = currNode.next;
                counter++;
            }
        }
        if (currNode == null)
            System.out.println((index + 1) + " position element not found");
    }

    @Override
    public String toString() {
        String res = "";
        Node currNode = this.head;
        while (currNode != null) {
            res += currNode.data + "\n";
            currNode = currNode.next;
        }
        return res;
    }
}
