package midterm_project;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Midterm {
    public static void main(String[] args) throws IOException {
        File file = new File();
        file.parse("testCase1.txt");
        System.out.println("WELCOME!!!");
        while (true) {
            start(file);
        }
    }

    public static void start(File file) throws IOException {
        System.out.println("please choose one method from menu:\n" +
                "1 -> save\n2 -> where\n3 -> next page\n4 -> previous page\n5 -> lines\n6 -> show\n" +
                "7 -> append\n8 -> insert\n9 -> remove\n10 -> replace\n11 -> swap\n12 -> find\n13 -> find & replace\n" +
                "14 -> undo\n15 -> redo\n16 -> exit\n17 -> print all");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        switch (n) {
            case 1:
                file.save("save.txt");
                break;
            case 2:
                System.out.println("you are on page " + file.pages.where());
                break;
            case 3:
                file.pages.nextPage();
                System.out.println("next page -> so you are on page " + file.pages.where());
                break;
            case 4:
                file.pages.previousPage();
                System.out.println("previous page -> so you are on page " + file.pages.where());
                break;
            case 5:
                System.out.println("lines in this page is: " + file.pages.lines());
                break;
            case 6:
                System.out.println("please enter number of lines that you want to watch: ");
                file.pages.show(scanner.nextInt());
                break;
            case 7:
                System.out.println("please enter text you want to append: (enter # for next line)");
                scanner.nextLine();
                file.pages.append(scanner.nextLine());
                break;
            case 8:
                System.out.println("please enter text you want to insert: ");
                String str = scanner.next();
                System.out.println("now enter number of line that you want to text add to it: ");
                file.pages.insert(str, scanner.nextInt());
                break;
            case 9:
                System.out.println("which line you want to remove ?");
                file.pages.remove(scanner.nextInt());
                break;
            case 10:
                System.out.println("please enter text you want to replace: ");
                String s = scanner.next();
                System.out.println("now enter number of line that you want to text replace: ");
                file.replace(scanner.nextInt(), s);
                break;
            case 11:
                System.out.println("please enter number of lines you want to swap: ");
                file.swap(scanner.nextInt(), scanner.nextInt());
                break;
            case 12:
                System.out.println("please enter text you want to find:");
                file.find(scanner.next());
                break;
            case 13:
                System.out.println("which string you want to find ?");
                String s1 = scanner.next();
                System.out.println("now enter text you want to replace: ");
                file.findAndReplace(s1, scanner.next());
                break;
            case 14:
                file.undo();
                break;
            case 15:
                file.redo();
                break;
            case 16:
                while (!File.undo.isEmpty())
                    file.undo();
                System.exit(0);
                break;
            case 17:
                file.pages.printList();
                break;
        }
    }
}

class File {
    Pages pages = new Pages();
    Lines page = new Lines();

    static StackUsingLinkedList undo = new StackUsingLinkedList();
    static StackUsingLinkedList redo = new StackUsingLinkedList();

    public void parse(String address) throws FileNotFoundException {
        java.io.File file = new java.io.File(address);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.equals("$")) {
                pages.insert(page);
                page = new Lines();
            } else
                page.insert(line);
        }
    }

    public void save(String address) throws IOException {
        FileWriter wr = new FileWriter(address);
        wr.write(pages.toString());
        wr.flush();
        wr.close();
        while (!undo.isEmpty())
            undo.pop();
        while (!redo.isEmpty())
            redo.pop();
    }

    public void replace(int n, String s) {
        Pages.Node x = pages.head;
        for (int i = 0; i < pages.where() - 1; i++)
            x = x.next;
        midterm_project.Node y = x.data.head;
        for (int i = 0; i < n - 1; i++)
            y = y.next;
        String[] str = {"replace", n + "", y.data};
        File.undo.push(str);
        y.data = s;
    }

    public void undoReplace(int n, String s) {
        Pages.Node x = pages.head;
        for (int i = 0; i < pages.where() - 1; i++)
            x = x.next;
        midterm_project.Node y = x.data.head;
        for (int i = 0; i < n - 1; i++)
            y = y.next;
        y.data = s;
    }

    public void swap(int n, int m) {
        Pages.Node x = pages.head;
        for (int i = 0; i < pages.where() - 1; i++)
            x = x.next;
        midterm_project.Node y = x.data.head;
        for (int i = 0; i < n - 1; i++)
            y = y.next;
        midterm_project.Node z = x.data.head;
        for (int i = 0; i < m - 1; i++)
            z = z.next;
        String[] str = {"swap", m + "", n + ""};
        File.undo.push(str);
        String temp = y.data;
        y.data = z.data;
        z.data = temp;
    }

    public void undoSwap(int n, int m) {
        Pages.Node x = pages.head;
        for (int i = 0; i < pages.where() - 1; i++)
            x = x.next;
        midterm_project.Node y = x.data.head;
        for (int i = 0; i < n - 1; i++)
            y = y.next;
        midterm_project.Node z = x.data.head;
        for (int i = 0; i < m - 1; i++)
            z = z.next;
        String temp = y.data;
        y.data = z.data;
        z.data = temp;
    }

    public void find(String s) {
        Pages.Node page = pages.head;
        int p = 1;
        while (page != null) {
            int l = 1;
            midterm_project.Node line = page.data.head;
            while (line != null) {
                if (line.data.contains(s)) {
                    System.out.println("find!  page: " + p + ", line: " + l + ", text :" + line.data);
                }
                line = line.next;
                l++;
            }
            page = page.next;
            p++;
        }
    }

    public void findAndReplace(String s, String t) {
        Pages.Node page = pages.head;
        while (page != null) {
            midterm_project.Node line = page.data.head;
            while (line != null) {
                if (line.data.contains(s)) {
                    line.data = line.data.replace(s, t);
                }
                line = line.next;
            }
            page = page.next;
        }
        String[] str = {"findAndReplace", t, s};
        File.undo.push(str);
    }

    public void undoFindAndReplace(String s, String t) {
        Pages.Node page = pages.head;
        while (page != null) {
            midterm_project.Node line = page.data.head;
            while (line != null) {
                if (line.data.contains(s)) {
                    line.data = line.data.replace(s, t);
                }
                line = line.next;
            }
            page = page.next;
        }
    }

    public void undo() {
        switch (undo.top.data[0]) {
            case "append":
                pages.notAppend(Integer.parseInt(undo.top.data[1]), Integer.parseInt(undo.top.data[2]));
                redo.push(undo.pop().data);
                break;
            case "insert":
                pages.undoRemove(Integer.parseInt(undo.top.data[1]));
                redo.push(undo.pop().data);
                break;
            case "remove":
                pages.undoInsert(undo.top.data[1], Integer.parseInt(undo.top.data[2]));
                redo.push(undo.pop().data);
                break;
            case "replace":
                undoReplace(Integer.parseInt(undo.top.data[1]), undo.top.data[2]);
                redo.push(undo.pop().data);
                break;
            case "swap":
                undoSwap(Integer.parseInt(undo.top.data[1]), Integer.parseInt(undo.top.data[2]));
                redo.push(undo.pop().data);
                break;
            case "findAndReplace":
                undoFindAndReplace(undo.top.data[1], undo.top.data[2]);
                redo.push(undo.pop().data);
                break;
            case "next":
                pages.previous();
                redo.push(undo.pop().data);
                break;
            case "previous":
                pages.next();
                redo.push(undo.pop().data);
                break;
        }
    }

    public void redo() {
        switch (redo.top.data[0]) {
            case "append":
                pages.append(redo.top.data[3]);
                redo.pop();
                break;
            case "insert":
                pages.insert(redo.top.data[3], Integer.parseInt(redo.top.data[2]));
                redo.pop();
                break;
            case "remove":
                pages.remove(Integer.parseInt(redo.top.data[2]));
                redo.pop();
                break;
            case "replace":
                replace(Integer.parseInt(redo.top.data[2]), redo.top.data[1]);
                redo.pop();
                break;
            case "swap":
                swap(Integer.parseInt(redo.top.data[2]), Integer.parseInt(redo.top.data[1]));
                redo.pop();
                break;
            case "findAndReplace":
                findAndReplace(redo.top.data[2], redo.top.data[1]);
                redo.pop();
                break;
            case "next":
                pages.nextPage();
                redo.pop();
                break;
            case "previous":
                pages.previousPage();
                redo.pop();
                break;
        }
    }
}



