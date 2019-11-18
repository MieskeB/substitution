package nl.michelbijnen.tool.substitution;

import nl.michelbijnen.tool.substitution.model.ConsoleColors;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WordChecker {
    private Set<String> wordsSet;

    public WordChecker() {
        try {
            Path path = Paths.get("words.txt");
            byte[] readBytes = Files.readAllBytes(path);
            String wordListContents = new String(readBytes, "UTF-8");
            String[] words = wordListContents.split("\n");
            wordsSet = new HashSet<>();
            Collections.addAll(wordsSet, words);
        }
        catch (Exception e) {
            System.out.print(ConsoleColors.RED);
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
