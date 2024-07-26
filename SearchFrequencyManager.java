package com.example.androidapp_1;

import java.util.HashMap;
import java.util.Map;

public class SearchFrequencyManager {
    private AVLTree avlTree;
    private Map<String, Integer> queryLog;

    public SearchFrequencyManager() {
        this.avlTree = new AVLTree();
        this.queryLog = new HashMap<>();
    }

    public AVLTree getAvlTree() {
        return avlTree;
    }

    public void insertQuery(String query) {
        String normalizedQuery = query.trim().toLowerCase();
        avlTree.insert(normalizedQuery);
    }

    public boolean searchQuery(String query) {
        String normalizedQuery = query.trim().toLowerCase();
        AVLTree.Node result = avlTree.search(normalizedQuery);
        if (result == null) {
            System.out.println("The query you are searching for does not exist.");
            return false;
        } else {
            avlTree.incrementFrequency(normalizedQuery);
            queryLog.put(normalizedQuery, result.frequency);
            System.out.println("Query found. Frequency updated.");
            return true;
        }
    }

    public Map<String, Integer> getQueryLog() {
        return queryLog;
    }

    public void displayAVLTree() {
        System.out.println("Current AVL Tree (Pre-Order Traversal):");
        avlTree.display();
    }


}
