package midterm_project;

import java.util.Arrays;

class StackUsingLinkedList {
     class Node {
        String[] data;
        Node link;
    }
    Node top;
    StackUsingLinkedList() {
        this.top = null;
    }
    public void push(String[] x)
    {
        Node temp = new Node();
        if (x == null) {
            System.out.println("item is null");
            return;
        }
        temp.data = x;
        temp.link = top;
        top = temp;
    }
    public boolean isEmpty() {
        return top == null;
    }
    public String[] peek() {
        if (isEmpty())
            return top.data;
        else {
            System.out.println("Stack is empty");
            return null;
        }
    }
    public Node pop()
    {
        if (top == null) {
            System.out.println("\nstack is empty");
            return null;
        }
        Node temp = top;
        top = top.link;
        temp.link = null;
        return temp;
    }

    public void display() {
        if (top == null)
            System.out.print("\nStack is empty");
        else {
            Node temp = top;
            while (temp != null) {
                System.out.println(Arrays.toString(temp.data));
                temp = temp.link;
                if (temp != null)
                    System.out.print(" -> ");
            }
        }
    }
}
