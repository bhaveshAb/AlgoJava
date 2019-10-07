import java.util.*;

public class AVL {
    static class Node {
        int data;
        Node left;
        Node right;
        int ht = 1;
        int bal = 0;

        public Node() {

        }

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    static Node construct(int[] sa, int lo, int hi) {
        if (lo > hi) {
            return null;
        }
        int mid = (lo + hi) / 2;
        Node root = new Node();
        root.data = sa[mid];
        root.left = construct(sa, lo, mid - 1);
        root.right = construct(sa, mid + 1, hi);
        root.ht = getht(root);
        root.bal = getbal(root);
        return root;
    }

    static int getht(Node node) {
        int lh = node.left != null ? node.left.ht : 0;
        int rh = node.right != null ? node.right.ht : 0;
        return Math.max(lh, rh) + 1;
    }

    static int getbal(Node node) {
        int lh = node.left != null ? node.left.ht : 0;
        int rh = node.right != null ? node.right.ht : 0;
        return (lh - rh);
    }

    static Node rightRotation(Node x) {
        Node y = x.left;
        Node t3 = y.right;
        x.left = t3;
        y.right = x;
        x.ht = getht(x);
        x.bal = getbal(x);
        y.ht = getht(y);
        y.bal = getbal(y);
        return y;
    }

    static Node leftRotation(Node x) {
        Node y = x.right;
        Node t2 = y.left;
        y.left = x;
        x.right = t2;
        x.ht = getht(x);
        x.bal = getbal(x);
        y.ht = getht(y);
        y.bal = getbal(y);
        return y;
    }

    static Node add(Node node, int data) {
        if (node == null) {
            return new Node(data, null, null);
        }
        if (data < node.data) {
            node.left = add(node.left, data);
        }
        if (data > node.data) {
            node.right = add(node.right, data);
        }
        node.ht = getht(node);
        node.bal = getbal(node);
        if (node.bal > 1) {
            if (node.left.bal >= 0) {
                node = rightRotation(node);
            } else {
                node.left = leftRotation(node.left);
                node = rightRotation(node);
            }
        } else if (node.bal < -1) {
            if (node.right.bal <= 0) {
                node = leftRotation(node);
            } else {
                node.right = rightRotation(node.right);
                node = leftRotation(node);
            }
        }
        return node;
    }

    static int max(Node root) {
        if (root.right == null) {
            return root.data;
        } else {
            return max(root.right);
        }

    }

    static Node remove(Node node, int data) {
        if (node == null) {
            return null;
        }
        if (data < node.data) {
            node.left = remove(node.left, data);
        } else if (data > node.data) {
            node.right = remove(node.right, data);
        } else {
            if (node.right == null || node.left == null) {
                node = node.left == null ? node.right : node.left;
            } else {
                int lmax = max(node.left);
                node.data = lmax;
                node.left = remove(node.left, lmax);
            }
        }
        if (node != null) {
            node.ht = getht(node);
            node.bal = getbal(node);
            if (node.bal > 1) {
                if (node.left.bal >= 0) {
                    node = rightRotation(node);
                } else {
                    node.left = leftRotation(node.left);
                    node = rightRotation(node);
                }
            } else if (node.bal < -1) {
                if (node.right.bal <= 0) {
                    node = leftRotation(node);
                } else {
                    node.right = rightRotation(node.right);
                    node = leftRotation(node);
                }
            }
        }

        return node;
    }

    static void display(Node node) {
        if (node == null) {
            return;
        }
        String str = "";
        str += node.left != null ? node.left.data + " -> " : " .-> ";
        str += node.data + "_" + node.ht + "_" + node.bal;
        str += node.right != null ? " <- " + node.right.data : " <-. ";
        System.out.println(str);
        display(node.left);
        display(node.right);
    }

    public static void main(String[] args) {
        int[] sa = { 12, 25, 37, 50, 62, 75, 87 };
        Node root = construct(sa, 0, sa.length - 1);
        add(root, 30);
        add(root, 28);
        add(root, 29);
        remove(root, 12);
        remove(root, 25);
        display(root);
    }
}