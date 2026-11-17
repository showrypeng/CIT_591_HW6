import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class EvilHangmanTest {

    // Play the game using test input.
    private String playGame(String guesses) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        try {
            System.setIn(
                    new ByteArrayInputStream(guesses.getBytes()));
            System.setOut(new PrintStream(output));

            EvilHangman game =
                    new EvilHangman("evilTestDictionary.txt");

            game.start();

        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        return output.toString();
    }

    @Test
    public void testNormalWin() {
        String output = playGame("c\na\nt\n");

        assertTrue(output.contains(
                "Congrats! The word was cat"));
    }

    @Test
    public void testInvalidLongInput() {
        String output = playGame("abc\nc\na\nt\n");

        assertTrue(output.contains(
                "Please enter a single character."));

        assertTrue(output.contains(
                "Congrats! The word was cat"));
    }

    @Test
    public void testRepeatedLetter() {
        String output = playGame("c\nc\na\nt\n");

        assertTrue(output.contains(
                "You've already guessed that."));

        assertTrue(output.contains(
                "Congrats! The word was cat"));
    }

    @Test
    public void testNonLetterAndWrongLetter() {
        String output = playGame("1\nz\nc\na\nt\n");

        assertTrue(output.contains(
                "Please enter an alphabetic character."));

        assertTrue(output.contains(
                "Incorrect guesses:\n[z]"));

        assertTrue(output.contains(
                "Congrats! The word was cat"));
    }
}
