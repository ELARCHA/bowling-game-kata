import exception.IllegalRollException;
import exception.TurnRollsExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BowlingScoreCalculatorTest {

    private BowlingScoreCalculator bowlingScoreCalculator;

    @BeforeEach
    void setUp() {
        bowlingScoreCalculator = new BowlingScoreCalculator();
    }

    @Test
    void rollWithIllegalRollTest() {
        assertThrows(IllegalRollException.class, () -> bowlingScoreCalculator.roll(11));
    }

    @Test
    public void TurnRollsExceededTest() {
        IntStream.rangeClosed(1, 20).forEach(i -> rollBowling(2));
        assertThrows(TurnRollsExceededException.class, () -> bowlingScoreCalculator.roll(1));
    }

    @Test
    public void bowlingWithNoPinHitWithOneRollTest() throws Exception {
        bowlingScoreCalculator.roll(0);
        assertEquals(0, bowlingScoreCalculator.calculateScore());
    }

    @Test
    public void bowlingWithAllTurnsOfOnesTest() {
        IntStream.rangeClosed(1, 20).forEach(i -> rollBowling(1));
        assertEquals(20, bowlingScoreCalculator.calculateScore());
    }

    @Test
    public void bowlingWithSpareTest() {
        rollBowling(8);
        rollBowling(2);
        rollBowling(7);
        rollBowling(1);
        assertEquals(25, bowlingScoreCalculator.calculateScore());
    }

    @Test
    public void bowlingWithMultipleSpareTest() {
        rollBowling(8);
        rollBowling(2);
        rollBowling(9);
        rollBowling(1);
        rollBowling(3);
        rollBowling(4);
        assertEquals(39, bowlingScoreCalculator.calculateScore());
    }

    @Test
    public void bowlingWithFullTurnsWithSomeMissTest() {
        rollBowling(9);
        rollBowling(0);
        rollBowling(9);
        rollBowling(0);
        rollBowling(9);
        rollBowling(0);
        rollBowling(9);
        rollBowling(0);
        rollBowling(9);
        rollBowling(0);
        rollBowling(9);
        rollBowling(0);
        rollBowling(9);
        rollBowling(0);
        rollBowling(9);
        rollBowling(0);
        rollBowling(9);
        rollBowling(0);
        rollBowling(9);
        rollBowling(0);
        assertEquals(90, bowlingScoreCalculator.calculateScore());
    }

    @Test
    public void bowlingWithAllTurnIsSpareTest() {
        IntStream.rangeClosed(1, 21).forEach(i -> rollBowling(5));
        assertEquals(150, bowlingScoreCalculator.calculateScore());
    }


    @Test
    public void bowlingWithStrikeTest() {
        rollBowling(10);
        rollBowling(3);
        rollBowling(4);
        assertEquals(24, bowlingScoreCalculator.calculateScore());
    }

    @Test
    public void bowlingWithAllTurnIsStrikeTest() {
        IntStream.rangeClosed(1, 12).forEach(i -> rollBowling(10));
        assertEquals(300, bowlingScoreCalculator.calculateScore());
    }

    @Test
    public void bowlingWithNormalGameTest() {
        rollBowling(9);
        rollBowling(1);
        rollBowling(3);
        rollBowling(5);
        rollBowling(7);
        rollBowling(2);
        rollBowling(6);
        rollBowling(1);
        rollBowling(8);
        rollBowling(0);
        rollBowling(9);
        rollBowling(0);
        rollBowling(10);
        rollBowling(5);
        rollBowling(5);
        rollBowling(4);
        rollBowling(3);
        rollBowling(1);
        rollBowling(0);
        assertEquals(96, bowlingScoreCalculator.calculateScore());
    }


    private void rollBowling(int i) {
        try {
            bowlingScoreCalculator.roll(i);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}