package com.example.androidapp_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class TrieNode {
    Map<Character, TrieNode> childNodes;
    boolean isEndOfWord;
    public List<String> links = new ArrayList<>();

    public TrieNode() {
        childNodes = new HashMap<>();
        isEndOfWord = false;
    }
}

class TrieAlgorithm {
    private TrieNode root;

    public TrieAlgorithm() {
        root = new TrieNode();
    }

    public void insert1(String word, String link) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current.childNodes.putIfAbsent(ch, new TrieNode());
            current = current.childNodes.get(ch);
        }
        current.isEndOfWord = true;
        current.links.add(link);
    }
    public List<String> search1(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.childNodes.get(c);
            if (current == null) {
                return new ArrayList<>();
            }
        }
        return current.isEndOfWord ? current.links : new ArrayList<>();
    }
    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.childNodes.putIfAbsent(ch, new TrieNode());
            node = node.childNodes.get(ch);
        }
        node.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.childNodes.get(ch);
            if (node == null) {
                return false;
            }
        }
        return node.isEndOfWord;
    }

    public Set<String> getAllWords() {
        Set<String> words = new HashSet<>();
        collectWords(root, "", words);
        return words;
    }

    private void collectWords(TrieNode node, String prefix, Set<String> words) {
        if (node.isEndOfWord) {
            words.add(prefix);
        }
        for (Map.Entry<Character, TrieNode> entry : node.childNodes.entrySet()) {
            collectWords(entry.getValue(), prefix + entry.getKey(), words);
        }
    }
}

