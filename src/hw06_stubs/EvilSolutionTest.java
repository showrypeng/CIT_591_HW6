import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class EvilSolutionTest {

    private ArrayList<String> words(String... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    private String progress(EvilSolution solution) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output));
            solution.printProgress();
        } finally {
            System.setOut(originalOut);
        }

        return output.toString().trim();
    }

    @Test
    public void testNewGame() {
        EvilSolution solution =
                new EvilSolution(words("cat", "dog"));

        assertFalse(solution.isSolved());
        assertEquals("_ _ _", progress(solution));
    }

    @Test
    public void testLargestWordFamily() {
        EvilSolution solution = new EvilSolution(
                words("echo", "heal", "belt", "peel", "hazy"));

        assertTrue(solution.addGuess('e'));
        assertEquals("_ e _ _", progress(solution));

        assertTrue(solution.getTarget().equals("heal")
                || solution.getTarget().equals("belt"));
    }

    @Test
    public void testIncorrectGuess() {
        EvilSolution solution =
                new EvilSolution(words("cat", "dog", "pig"));

        assertFalse(solution.addGuess('z'));
        assertEquals("_ _ _", progress(solution));
        assertFalse(solution.isSolved());
    }

    @Test
    public void testTiePrefersNoLetters() {
        EvilSolution solution =
                new EvilSolution(words("cat", "dog"));

        assertFalse(solution.addGuess('c'));
        assertEquals("_ _ _", progress(solution));
        assertEquals("dog", solution.getTarget());
    }

    @Test
    public void testRevealRepeatedLetters() {
        EvilSolution solution =
                new EvilSolution(words("peep"));

        assertTrue(solution.addGuess('e'));
        assertEquals("_ e e _", progress(solution));
        assertFalse(solution.isSolved());
    }

    @Test
    public void testPreviousLettersStayVisible() {
        EvilSolution solution =
                new EvilSolution(words("heal", "belt"));

        assertTrue(solution.addGuess('e'));
        assertTrue(solution.addGuess('l'));

        String currentProgress = progress(solution);

        assertTrue(currentProgress.contains("e"));
        assertTrue(currentProgress.contains("l"));
    }

    @Test
    public void testFinishGame() {
        EvilSolution solution =
                new EvilSolution(words("cat"));

        assertTrue(solution.addGuess('c'));
        assertTrue(solution.addGuess('a'));
        assertFalse(solution.isSolved());

        assertTrue(solution.addGuess('t'));
        assertTrue(solution.isSolved());

        assertEquals("cat", solution.getTarget());
        assertEquals("c a t", progress(solution));
    }

    @Test
    public void testWordsOnlyGetNarrower() {
        EvilSolution solution =
                new EvilSolution(words("heal", "belt", "hazy"));

        assertTrue(solution.addGuess('e'));
        assertFalse(solution.addGuess('z'));

        assertEquals("_ e _ _", progress(solution));

        assertTrue(solution.getTarget().equals("heal")
                || solution.getTarget().equals("belt"));
    }
}
