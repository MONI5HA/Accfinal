package com.example.androidapp_1;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class SpellChecker {

    private TrieAlgorithm trie;
    private Set<String> vocabulary;

    public SpellChecker() {
        trie = new TrieAlgorithm();
        vocabulary = new HashSet<>();
    }

    public void loadVocabulary(Context context) {
        vocabulary = buildVocabulary(context);
        for (String word : vocabulary) {
            trie.insert(word);
        }
    }

    private Set<String> buildVocabulary(Context context) {
        Set<String> vocabSet = new HashSet<>();
        Resources resources = context.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.pagetext);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                createVocabularyForLine(line, vocabSet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vocabSet;
    }

    private void createVocabularyForLine(String line, Set<String> vocabulary) {
        String[] words = line.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                vocabulary.add(word);
            }
        }
    }

    public String checkWord(String word) {
        if (trie.search(word)) {
            return "";
        } else {
            String suggestion = getClosestMatch(word);
            return suggestion.isEmpty() ? "" : "Do you mean \"" + suggestion + "\"?";
        }
    }

    private String getClosestMatch(String input) {
        String closestMatch = "";
        int minDistance = Integer.MAX_VALUE;

        for (String word : trie.getAllWords()) {
            int distance = calculateEditDistance(input, word);
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = word;
            }
        }

        return closestMatch;
    }

    private int calculateEditDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (word1.charAt(i - 1) == word2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    public Set<String> getVocabulary() {
        return vocabulary;
    }
}
