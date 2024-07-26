package com.example.androidapp_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PageRankingSystem2 {
    private List<String> stopWords; // list of stop words

    // Constructor
    public PageRankingSystem2() {
        // Initialize stop words
        stopWords = Arrays.asList("a", "an", "the", "and", "or", "but", "if", "is", "are", "of", "in", "on", "for", "with", "to", "from", "by", "at", "as", "about");
    }

    // Method to extract keywords from the content
    public List<String> extractKeywords(String content) {
        // Convert content to lowercase and replace non-alphanumeric characters with spaces
        String[] words = content.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");
        List<String> keywords = new ArrayList<>();
        for (String word : words) {
            // Ignore stop words and words with length less than 2
            if (!stopWords.contains(word) && word.length() > 1) {
                keywords.add(word);
            }
        }
        return keywords;
    }

    // Method to calculate the rank of a page based on search keywords
    public int calculatePageRank(AVLTree tree, List<String> searchKeywords) {
        int rank = 0;
        try {
            for (String keyword : searchKeywords) {
                for (AVLTree.Node node : tree.getInOrderList()) {
                    if (node.key.equals(keyword)) {
                        rank += node.frequency; // add frequency of the keyword to the rank
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error calculating page rank: " + e.getMessage());
            e.printStackTrace();
        }
        return rank;
    }

    // Method to sort pages in descending order of rank using QuickSort
    public void quickSort(List<Page> pages, int low, int high) {
        if (low < high) {
            int pi = partition(pages, low, high);
            quickSort(pages, low, pi - 1);
            quickSort(pages, pi + 1, high);
        }
    }

    // Partition method for QuickSort
    private int partition(List<Page> pages, int low, int high) {
        int pivot = pages.get(high).rank;
        int i = (low - 1); // Index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is greater than or equal to pivot (for descending order)
            if (pages.get(j).rank >= pivot) {
                i++;
                // Swap pages[i] and pages[j]
                Page temp = pages.get(i);
                pages.set(i, pages.get(j));
                pages.set(j, temp);
            }
        }
        // Swap pages[i+1] and pages[high] (or pivot)
        Page temp = pages.get(i + 1);
        pages.set(i + 1, pages.get(high));
        pages.set(high, temp);

        return i + 1;
    }

    // Method to display pages in descending order of rank
    public void displayPages(List<Page> pages) {
        // Sort pages by rank in descending order using QuickSort
        quickSort(pages, 0, pages.size() - 1);

        // Display pages and their ranks
        for (Page page : pages) {
            System.out.println("URL: " + page.url);
            System.out.println("Rank: " + page.rank);
            System.out.println("--------------------------------------------");
        }
    }

    // Method to fetch HTML content from a given URL
    public String fetchHTMLContent(String url) throws IOException {
        StringBuilder content = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    // Method to extract text from HTML content
    public String extractTextFromHTML(String html) {
        // Remove HTML tags
        return html.replaceAll("<[^>]*>", " ");
    }

    // Main method to execute the page ranking system
    public static void main(String[] args) {
        PageRankingSystem2 prs = new PageRankingSystem2();

        // List of URLs to be processed
        List<String> urls = Arrays.asList(
                "https://www.fit4less.ca/",
                "https://www.worldgym.com/",
                "https://www.fit4less.ca/join-now",
                "https://www.fit4less.ca/memberships/lock-in-your-rate",
                "https://www.fit4less.ca/memberships/free-workout",
                "https://www.fit4less.ca/memberships/black-card-workout-area",
                "https://www.fit4less.ca/faq/membership-changes",
                "https://www.fit4less.ca/join-now-flow?gym=272#",
                "https://www.worldgym.com/gyms/",
                "https://www.worldgym.com/classes/yoga/",
                "https://www.worldgym.com/classes/cycling/",
                "https://www.worldgym.com/classes/strength/",
                "https://www.worldgym.com/classes/hiit/",
                "https://www.worldgym.com/classes/zumba/",
                "https://www.worldgym.com/classes/les-mills/",
                "https://www.worldgym.com/classes/",
                "https://www.worldgym.com/training/",
                "https://www.worldgym.com/training/world-gym-athletics/",
                "https://www.worldgym.com/training/personal-training/",
                "https://www.worldgym.com/video-center/workout-tips/",
                "https://www.worldgym.com/about/",
                "https://www.worldgym.com/about/photo-gallery/",
                "https://www.worldgym.com/blog/",
                "https://www.worldgym.com/about/testimonials/",
                "https://www.worldgymfranchising.com/",
                "https://shopworldgym.com/",
                "https://www.worldgym.com/barrie/classes/schedule/",
                "https://webdesignmastery.github.io/Fitclub_25-07-23/#"
                // Add your URLs here
        );

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a keyword to search: ");
        String keyword = scanner.nextLine().toLowerCase(); // read keyword input

        // Extract keywords from page content
        List<String> searchKeywords = new ArrayList<>();
        searchKeywords.add(keyword);

        List<Page> matchedPages = new ArrayList<>();

        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            String pageContent = "";

            try {
                pageContent = prs.fetchHTMLContent(url);
            } catch (IOException e) {
                System.err.println("Error fetching content from URL: " + e.getMessage());
                e.printStackTrace();
                continue; // Skip this URL if fetching fails
            }

            // Extract text from HTML content
            String textContent = prs.extractTextFromHTML(pageContent);

            // Extract keywords from page content
            List<String> keywords = prs.extractKeywords(textContent);
            AVLTree tree = new AVLTree(); // create an AVL tree
            for (String kw : keywords) {
                tree.insert(kw); // insert keywords into the AVL tree
            }

            // Calculate the rank of the page based on search keywords
            int rank = prs.calculatePageRank(tree, searchKeywords);

            // Create a Page object with the page content, its rank, and the search keywords
            Page page = new Page(url, rank, searchKeywords, i + 1); // i + 1 gives the 1-based page number
            matchedPages.add(page);
        }

        // Display pages in descending order of rank
        prs.displayPages(matchedPages);

        // Close scanner
        scanner.close();
    }
}