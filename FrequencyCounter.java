package com.example.androidapp_1;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FrequencyCounter {

    private String moText;

    public FrequencyCounter(Context context) throws IOException {
        this.moText = readTextFile(context);
    }

    private String readTextFile(Context context) throws IOException {
        StringBuilder moSb = new StringBuilder();
        Resources resources = context.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.pagetext);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                moSb.append(line).append("\n");
            }
        }
        return moSb.toString();
    }

    public int countWordFrequency(String word) {
        int moN = this.moText.length();
        int moM = word.length();
        int moCount = 0;

        if (moM == 0) return moCount;

        Map<Character, Integer> moBadChar = new HashMap<>();

        // Bad Character heuristic
        for (int moI = 0; moI < moM; moI++) {
            moBadChar.put(word.charAt(moI), moI);
        }

        int moS = 0;
        while (moS <= (moN - moM)) {
            int moJ = moM - 1;

            while (moJ >= 0 && word.charAt(moJ) == moText.charAt(moS + moJ)) {
                moJ--;
            }

            if (moJ < 0) {
                moCount++;
                moS += (moS + moM < moN) ? moM - moBadChar.getOrDefault(moText.charAt(moS + moM), -1) : 1;
            } else {
                moS += Math.max(1, moJ - moBadChar.getOrDefault(moText.charAt(moS + moJ), -1));
            }
        }

        return moCount;
    }
}