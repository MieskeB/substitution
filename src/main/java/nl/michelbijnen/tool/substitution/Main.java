package nl.michelbijnen.tool.substitution;

import nl.michelbijnen.tool.substitution.model.ConsoleColors;
import nl.michelbijnen.tool.substitution.model.Hint;
import nl.michelbijnen.tool.substitution.model.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // Starting info
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Substitution tool v1.0 by MieskeB");
        System.out.println("If you have feedback about this tool, please contact me on Discord: MieskeB#0799");

        // Encoded string
        System.out.print(ConsoleColors.BLUE + "What is the string that needs to be decoded? (Use white spaces for the word separator) ");
        String encoded = in.nextLine();
        System.out.println(ConsoleColors.GREEN + "Encoded string successfully saved!");

        // Hints
        List<Hint> hints;
        String hintsPlain;
        while (true) {
            System.out.print(ConsoleColors.BLUE + "Do you have any letters which you already know? (format: r=g,a=p,p=r) ");
            hintsPlain = in.nextLine();
            try {
                hints = convertPlainToList(hintsPlain);
                System.out.println(ConsoleColors.GREEN + "Hints successfully saved!");
                break;
            } catch (Exception Ignored) {
                if (hintsPlain.equals("")) {
                    hints = new ArrayList<>();
                    System.out.println(ConsoleColors.GREEN + "Hints successfully saved!");
                    break;
                }
                System.out.println(ConsoleColors.RED + "Please use this format: a=r,b=w,w=c. Do not use any spaces!");
            }
        }

        // Times
        int times;
        while (true) {
            System.out.print(ConsoleColors.BLUE + "How many results do you want to try? ");
            try {
                times = Integer.parseInt(in.nextLine());
                System.out.println(ConsoleColors.GREEN + "Amount of times successfully saved!");
                break;
            } catch (Exception Ignored) {
                System.out.println(ConsoleColors.RED + "Something went wrong, did you input a valid number format?");
            }
        }

        // WordChecker
        boolean withWordChecker;
        label:
        while (true) {
            System.out.print(ConsoleColors.BLUE + "With a word checker? (Takes longer to calculate) ");
            String input = in.nextLine();
            switch (input.toLowerCase()) {
                case "y":
                case "yes":
                case "true":
                case "t":
                    withWordChecker = true;
                    break label;
                case "n":
                case "no":
                case "false":
                case "f":
                    withWordChecker = false;
                    break label;
                default:
                    System.out.println(ConsoleColors.RED + "Please input only yes (yes, y, true or t) or no (no, n, false or f)");
                    break;
            }
        }

        // Solver
        Solver solver = new Solver();
        while (true) {
            System.out.print(ConsoleColors.BLUE + "Starting decoding process now for encoded string " + encoded);
            if (hints.size() != 0) {
                System.out.print(" with hints " + hintsPlain);
            }
            System.out.println(" trying for " + times + " random solutions");

            List<Solution> possibleSolutions = solver.getPossibleSolutions(encoded, hints, times, withWordChecker);

            if (possibleSolutions.size() != times) {
                System.out.println(ConsoleColors.RED + "Stopping early because cannot find any more solutions. Tried " + possibleSolutions.size() + " times of " + times);
            }

            for (Solution solution : possibleSolutions) {
                System.out.print(ConsoleColors.CYAN_BRIGHT + solution.getDecoded());
                System.out.print(ConsoleColors.CYAN + " (Key:" + solution.getKey());
                if (!solution.getContains().equals("")) {
                    System.out.print(" contains word: " + solution.getContains());
                }
                System.out.print(")");
                in.nextLine();
            }

            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "That's all! Do you want to continue?");
            System.out.println("If yes, input a valid amount of times you want to have more");
            System.out.println("If no, input anything else");
            String res = in.nextLine();

            try {
                times = Integer.parseInt(res);
            } catch (Exception Ignored) {
                System.out.println("Thank you for using this tool!");
                break;
            }
        }
    }

    private static List<Hint> convertPlainToList(String plain) {
        String[] plainList = plain.split(",");
        List<Hint> result = new ArrayList<>();
        for (String item : plainList) {
            String[] bothItems = item.split("=");
            result.add(new Hint(bothItems[0].toCharArray()[0], bothItems[1].toCharArray()[0]));
        }
        return result;
    }
}
