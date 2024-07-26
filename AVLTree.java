package com.example.androidapp_1;

import java.util.ArrayList;
import java.util.List;

public class AVLTree {
    // Node class representing each node in the AVL tree
    class Node {
        String key;
        int frequency;
        int height;
        Node left, right;

        Node(String d) {
            key = d;
            frequency = 1;
            height = 1;
        }
    }

    private Node root;

    // Get the height of the node
    int height(Node N) {
        if (N == null)
            return 0;
        return N.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }
    // Rotate right
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Rotate left
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Get the balance factor of the node
    int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    // Insert a keyword into the AVL tree
    public void insert(String key) {
        root = insertRec(root, key);
    }

    private Node insertRec(Node node, String key) {
        if (node == null)
            return (new Node(key));

        if (key.compareTo(node.key) < 0)
            node.left = insertRec(node.left, key);
        else if (key.compareTo(node.key) > 0)
            node.right = insertRec(node.right, key);
        else {
            node.frequency++;
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && key.compareTo(node.left.key) < 0)
            return rightRotate(node);

        if (balance < -1 && key.compareTo(node.right.key) > 0)
            return leftRotate(node);

        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // In-order traversal to get all nodes in sorted order
    public List<Node> getInOrderList() {
        List<Node> nodeList = new ArrayList<>();
        inOrderRec(root, nodeList);
        return nodeList;
    }

    private void inOrderRec(Node node, List<Node> nodeList) {
        if (node != null) {
            inOrderRec(node.left, nodeList);
            nodeList.add(node);
            inOrderRec(node.right, nodeList);
        }
    }


    // Function to search a key in the AVL tree
    public Node search(Node root, String key) {
        // Base Cases: root is null or key is present at root
        if (root == null || root.key.equals(key))
            return root;

        // Key is greater than root's key
        if (root.key.compareTo(key) < 0)
            return search(root.right, key);

        // Key is smaller than root's key
        return search(root.left, key);
    }

    public Node search(String key) {
        return search(root, key);
    }

    // Function to increment the frequency of a key
    public void incrementFrequency(String key) {
        Node node = search(root, key);
        if (node != null) {
            node.frequency++;
        }
    }

    // A utility function to do preorder traversal of the tree
    public void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void display() {
        preOrder(root);
    }
}
