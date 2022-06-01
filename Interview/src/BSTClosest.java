public class BSTClosest {

    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int v) {
            val = v;
        }
    }

    Node root;

    BSTClosest() {
        root = null;
    }

    BSTClosest(int v) {
        root = new Node(v);
    }

    void insert(int v) {
        root = insert(root, v);
    }

    Node insert(Node root, int v) {
        if (root == null) {
            root = new Node(v);
            return root;
        }

        if (root.val > v) {
            root.left = insert(root.left, v);
        }

        if (root.val < v) {
            root.right = insert(root.right, v);
        }

        return root;
    }

    void inorder() {
        inorder(root);
    }

    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.val + "\t");
            inorder(root.right);
        }
    }

    Node closest(int v) {
        return closest(root, v, Integer.MAX_VALUE, root);
    }

    Node closest(Node r, int v, int currDiff, Node curr) {
        if (r == null)
            return curr;

        int newDiff = currDiff;
        Node newNode = curr;
        int d = r.val - v;
        int absD = Math.abs(d);
        if (absD < currDiff) {
            newDiff = currDiff;
            newNode = r;
        }
        if (d == 0) {
            return r;
        } else if (d < 0) {
            return closest(r.right, v, newDiff, newNode);
        } else {
            return closest(r.left, v, newDiff, newNode);
        }
    }

    public static void main(String[] args) {
        BSTClosest b = new BSTClosest();

        int[] s = new int[]{10,5,15,2,5,13,22,14,1};
        for(int i = 0; i< s.length; ++i){
            b.insert(s[i]);
        }

        b.inorder();

        System.out.println(b.closest(12).val);
    }

}
