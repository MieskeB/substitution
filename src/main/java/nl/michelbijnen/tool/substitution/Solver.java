package nl.michelbijnen.tool.substitution;

import nl.michelbijnen.tool.substitution.model.ConsoleColors;
import nl.michelbijnen.tool.substitution.model.Hint;
import nl.michelbijnen.tool.substitution.model.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Solver {

    private final boolean debug = false;

    private char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private List<Hint> hints;
    private HistoryKeeper historyKeeper;
    private WordChecker wordChecker;

    public Solver(boolean withWordChecker, int minWordLength) {
        this.historyKeeper = new HistoryKeeper();
        if (withWordChecker) {
            this.wordChecker = new WordChecker(minWordLength);
        }
    }

    public List<Solution> getPossibleSolutions(String encoded, List<Hint> hints, int times) {
        this.hints = hints;
        this.charsWithHints();
        encoded = encoded.toLowerCase();
        List<Solution> solutions = new ArrayList<>();

        if (this.debug) System.out.println(ConsoleColors.MAGENTA + "Encoded: " + encoded + ConsoleColors.RESET);

        for (int i1 = 0; i1 < times; i1++) {

            try {
                this.randomize();
            } catch (StackOverflowError Ignored) {
                return solutions;
            }

            if (this.debug) System.out.println(ConsoleColors.MAGENTA + "Key: " + String.valueOf(this.chars) + ConsoleColors.RESET);

            char[] decoded = encoded.toCharArray();

            for (char aChar : this.chars) {
                char character = (char) (this.getCharLocation(aChar) + 97);
                for (int i2 = 0; i2 < decoded.length; i2++) {
                    if (encoded.toCharArray()[i2] == character) {
                        decoded[i2] = aChar;
                    }
                }
            }

            Solution solution = new Solution(encoded, String.valueOf(decoded), String.valueOf(this.chars));

            if (this.debug) System.out.println(ConsoleColors.MAGENTA + "Decoded: " + String.valueOf(decoded) + ConsoleColors.RESET);

            if (this.wordChecker != null) {
                String word = this.wordChecker.checkIfStringContainsEnglishWord(solution.getDecoded());
                if (this.debug) System.out.println(ConsoleColors.MAGENTA + "Word: " + word + ConsoleColors.RESET);
                if (word.equals("")) {
                    i1--;
                    continue;
                }

                solution.setContains(word);
            }

            solutions.add(solution);
        }

        return solutions;
    }

    private void charsWithHints() {
        for (Hint hint : this.hints) {
            char temp = this.chars[hint.getFromLocation()];
            int indexTo = this.getCharLocation(hint.getTo());
            this.chars[hint.getFromLocation()] = hint.getTo();
            this.chars[indexTo] = temp;
        }
    }

    private void randomize() throws StackOverflowError {
        // Creating a object for Random class
        Random r = new Random();

        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = this.chars.length - 1; i > 0; i--) {

            // Pick a random index from 0 to i
            int j = r.nextInt(i);

            // Swap arr[i] with the element at random index
            char temp = this.chars[i];
            this.chars[i] = this.chars[j];
            this.chars[j] = temp;
        }

        // Prints the random array
        this.charsWithHints();

        if (!this.historyKeeper.checkArray(this.chars)) {
            this.randomize();
        }
    }

    private int getCharLocation(char character) {
        return IntStream.range(0, this.chars.length).filter(i -> this.chars[i] == character).findFirst().orElse(-1);
    }
}
