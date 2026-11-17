import java.util.ArrayList;

public class EvilHangman extends Hangman {

    public EvilHangman() {
        this("engDictionary.txt");
    }

    public EvilHangman(String filename) {
        super(filename);

        // Use the original random word to decide the length.
        int wordLength = solution.getTarget().length();

        ArrayList<String> sameLengthWords = new ArrayList<>();

        for (String word : wordList) {
            if (word.length() == wordLength) {
                sameLengthWords.add(word);
            }
        }

        // Replace the normal solution with an evil solution.
        solution = new EvilSolution(sameLengthWords);
    }
}
