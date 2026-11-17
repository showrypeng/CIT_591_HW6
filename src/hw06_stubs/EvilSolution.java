import java.util.ArrayList;
import java.util.HashMap;

public class EvilSolution extends Solution {

    private ArrayList<String> possibleWords;

    public EvilSolution(ArrayList<String> wordList) {
        super(wordList.get(0));
        possibleWords = new ArrayList<>(wordList);
    }

    @Override
    public boolean isSolved() {
        return super.isSolved();
    }

    @Override
    public void printProgress() {
        super.printProgress();
    }

    @Override
    public boolean addGuess(char guess) {

        HashMap<String, ArrayList<String>> families = new HashMap<>();

        // Group words based on where the guessed letter appears.
        for (String word : possibleWords) {
            String pattern = makePattern(word, guess);

            if (!families.containsKey(pattern)) {
                families.put(pattern, new ArrayList<>());
            }

            families.get(pattern).add(word);
        }

        // Find the largest family.
        String bestPattern = null;
        int biggestSize = 0;

        for (String pattern : families.keySet()) {
            int familySize = families.get(pattern).size();

            if (bestPattern == null || familySize > biggestSize) {
                bestPattern = pattern;
                biggestSize = familySize;
            } else if (familySize == biggestSize
                    && countLetters(pattern, guess)
                    < countLetters(bestPattern, guess)) {
                bestPattern = pattern;
            }
        }

        // Keep only the words in the chosen family.
        possibleWords = families.get(bestPattern);
        target = possibleWords.get(0);

        // Update the letters that the player can see.
        boolean correct = false;

        for (int i = 0; i < bestPattern.length(); i++) {
            if (bestPattern.charAt(i) == guess
                    && partialSolution.get(i) == '_') {
                partialSolution.set(i, guess);
                missingChars--;
                correct = true;
            }
        }

        return correct;
    }

    @Override
    public String getTarget() {
        return super.getTarget();
    }

    private String makePattern(String word, char guess) {
        String pattern = "";

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                pattern += guess;
            } else {
                pattern += partialSolution.get(i);
            }
        }

        return pattern;
    }

    private int countLetters(String pattern, char guess) {
        int count = 0;

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == guess) {
                count++;
            }
        }

        return count;
    }
}
