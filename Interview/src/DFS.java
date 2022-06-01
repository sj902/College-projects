import java.util.Stack;

class Node {
    int val;
    Node left, right;

    public Node(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class DFS {


    Node root;

    DFS() {
        root = null;
    }

    DFS(int v) {
        root = new Node(v);
    }


    void traverse() {
        traverse(root, 0);
    }

    void traverse(Node root, int sum) {
        if (root == null) {
            return;
        }

        int res = sum + root.val;

        if (root.left == null && root.right == null) {
            System.out.println(res);
        }

        if (root.left != null) {
            traverse(root.left, res);
        }

        if (root.right != null) {
            traverse(root.right, res);
        }
    }

    public static void main(String[] args) {
        DFS tree = new DFS();
        tree.root = new Node(6);
        tree.root.left = new Node(3);
        tree.root.right = new Node(5);
        tree.root.right.right = new Node(4);
        tree.root.left.left = new Node(2);
        tree.root.left.right = new Node(5);
        tree.root.left.right.right = new Node(4);
        tree.root.left.right.left = new Node(7);

        tree.traverse();
    }

}
