import exception.IllegalRollException;
import exception.TurnRollsExceededException;
import model.Turn;
import model.TurnType;

import java.util.ArrayList;
import java.util.List;

public class BowlingScoreCalculator {

    public static final int LAST_TURN_INDEX = 9;
    int currentTurnIndex;
    List<Turn> turns = new ArrayList<>();

    public BowlingScoreCalculator() {
        Turn currentTurn = new Turn();
        turns.add(currentTurn);
        for (int i = 1; i <= LAST_TURN_INDEX; i++) {
            Turn nextTurn = new Turn();
            currentTurn.setNextTurn(nextTurn);
            currentTurn = nextTurn;
            turns.add(nextTurn);
        }
        this.currentTurnIndex = 0;
    }

    public void roll(int pins) throws IllegalRollException, TurnRollsExceededException {
        if (currentTurnIndex == LAST_TURN_INDEX && isSpareOrStrike()) {
            Turn nextTurn = new Turn();
            nextTurn.setFinalTurnType(getFinalTurnType());
            turns.add(nextTurn);
            turns.get(currentTurnIndex).setNextTurn(nextTurn);
            currentTurnIndex++;
        }
        turns.get(currentTurnIndex).roll(pins);
        if (!turns.get(currentTurnIndex).hasTurnRolls()
                && (currentTurnIndex < LAST_TURN_INDEX)) {
            currentTurnIndex++;
        }
    }

    private boolean isSpareOrStrike() {
        return turns.get(currentTurnIndex).isStrike() || turns.get(currentTurnIndex).isSpare();
    }

    private TurnType getFinalTurnType() {
        return turns.get(currentTurnIndex).isStrike() ? TurnType.STRIKE : TurnType.SPARE;
    }


    public int calculateScore() {
        return turns.stream().mapToInt(Turn::calculateScore).sum();
    }
}
