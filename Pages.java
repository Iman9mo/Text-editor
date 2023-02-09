package midterm_project;

public class Pages {
    Node head;
    Node currentPage;
    int pageNumber = 1;
    int pages = 0;

    class Node {
        Lines data;
        Node next;

        Node(Lines d) {
            data = d;
            next = null;
        }
    }

    public void insert(Lines page) {
        Node new_node = new Node(page);
        new_node.next = null;
        if (head == null) {
            head = new_node;
            currentPage = head;
            pages = 1;
        } else {
            Node last = head;
            while (last.next != null)
                last = last.next;
            last.next = new_node;
            pages++;
        }
    }

    public void append(String s) {
        String[] arr = s.split("#");
        Node x = head;
        for (int i = 1; i < where(); i++)
            x = x.next;
        for (String value : arr) x.data.insert(value);
        String[] str = {"append", where() + "", arr.length + "", s};
        File.undo.push(str);
    }

    public void notAppend(int pageNumber, int parts) {
        Node x = head;
        for (int i = 1; i < pageNumber; i++)
            x = x.next;
        midterm_project.Node y = x.data.head;
        int l = 1;
        while (y != null) {
            y = y.next;
            l++;
        }
        for (int i = 1; i < parts + 1; i++)
            x.data.deleteAtPosition(l - i - 1);
    }

    public void insert(String s, int n) {
        Node x = head;
        for (int i = 1; i < where(); i++)
            x = x.next;
        midterm_project.Node y = x.data.head;
        if (n <= 1) {
            midterm_project.Node node = new midterm_project.Node(s);
            node.next = x.data.head;
            x.data.head = node;
            return;
        }
        for (int i = 0; i < n - 2; i++)
            y = y.next;
        midterm_project.Node next = y.next;
        midterm_project.Node line = new midterm_project.Node(s);
        y.next = line;
        line.next = next;
        String[] str = {"insert", n + "", s};
        File.undo.push(str);
    }

    public void undoInsert(String s, int n) {
        Node x = head;
        for (int i = 1; i < where(); i++)
            x = x.next;
        midterm_project.Node y = x.data.head;
        for (int i = 0; i < n - 2; i++)
            y = y.next;
        midterm_project.Node next = y.next;
        midterm_project.Node line = new midterm_project.Node(s);
        y.next = line;
        line.next = next;
    }

    public void remove(int n) {
        Node x = head;
        for (int i = 0; i < where() - 1; i++)
            x = x.next;
        midterm_project.Node y = x.data.head;
        for (int i = 0; i < n - 1; i++)
            y = y.next;
        String[] str = {"remove", y.data, n + ""};
        File.undo.push(str);
        x.data.deleteAtPosition(n - 1);
    }

    public void undoRemove(int n) {
        Node x = head;
        for (int i = 0; i < where() - 1; i++)
            x = x.next;
        midterm_project.Node y = x.data.head;
        for (int i = 0; i < n - 1; i++)
            y = y.next;
        x.data.deleteAtPosition(n - 1);
    }

    public void printList() {
        Node currNode = this.head;
        System.out.println("print all: ");
        while (currNode != null) {
            currNode.data.printList();
            System.out.println("$");
            currNode = currNode.next;
        }
    }

    @Override
    public String toString() {
        String res = "";
        Node currNode = this.head;
        while (currNode != null) {
            res += currNode.data.toString();
            res += "$\n";
            currNode = currNode.next;
        }
        return res;
    }

    public int where() {
        return pageNumber;
    }

    public void nextPage() {
        File.undo.push(new String[]{"next"});
        this.pageNumber++;
        System.out.println("current page:" + this.pageNumber);
    }

    public void previousPage() {
        File.undo.push(new String[]{"previous"});
        this.pageNumber--;
        System.out.println("current page:" + this.pageNumber);
    }

    public void next() {
        this.pageNumber++;
        System.out.println("current page:" + this.pageNumber);
    }

    public void previous() {
        this.pageNumber--;
        System.out.println("current page:" + this.pageNumber);
    }

    public int lines() {
        Node x = head;
        for (int i = 1; i < pageNumber; i++)
            x = x.next;
        return x.data.numberOfLines;
    }

    public void show(int n) {
        Node x = head;
        for (int i = 1; i < pageNumber; i++)
            x = x.next;
        x.data.show(n);
    }
}
