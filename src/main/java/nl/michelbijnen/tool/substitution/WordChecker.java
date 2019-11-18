package nl.michelbijnen.tool.substitution;

import nl.michelbijnen.tool.substitution.model.ConsoleColors;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class WordChecker {
    private Set<String> wordsSet;

    public WordChecker(int minWordLength) {
        try {
            try (InputStream resource = getClass().getResourceAsStream("/words.txt")) {
                this.wordsSet = new HashSet<>(new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().collect(Collectors.toList()));
            }

            for (String word : new HashSet<>(this.wordsSet)) {
                if (word.length() < minWordLength) {
                    this.wordsSet.remove(word);
                }
            }
        } catch (Exception e) {
            System.out.print(ConsoleColors.RED);
            System.out.println(e.getMessage());
            for (int i = 0; i < 2147483647; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
                System.out.print("ERROR");
            }
        }
    }

    public String checkIfStringContainsEnglishWord(String input) {
        for (String word : wordsSet) {
            if (input.contains(word)) {
                if (word.length() >= 4) {
                    return word;
                }
            }
        }
        return "";
    }
}
