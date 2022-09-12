package model;

import exception.IllegalRollException;
import exception.TurnRollsExceededException;
import lombok.Getter;
import lombok.Setter;

import static model.TurnType.SPARE;

@Getter
public class Turn {

    private static final int MAX_NUMBER_OF_PINS = 10;
    private static final int MAX_TRIES_IN_A_TURN = 2;
    private int firstRoll;
    private int secondRoll;
    private int triesNum = 0;

    @Setter
    private TurnType finalTurnType;
    @Setter
    private Turn nextTurn;


    final void setFirstRoll(int roll) {
        firstRoll = roll;
        triesNum++;
    }

    final void setSecondRoll(int roll) {
        secondRoll = roll;
        triesNum++;
    }

    public boolean isStrike() {
        return firstRoll == 10;
    }

    public boolean isSpare() {
        return firstRoll + secondRoll == 10;
    }

    final void checkNumberOfPins(int pins) throws IllegalRollException {
        if (pins > 10) {
            throw new IllegalRollException();
        }
    }


    public void roll(int pins) throws TurnRollsExceededException, IllegalRollException {
        checkNumberOfPins(pins);
        if (getTriesNum() >= MAX_TRIES_IN_A_TURN || (SPARE.equals(finalTurnType) && getTriesNum() == 1)) {
            throw new TurnRollsExceededException();
        }

        if ((getFirstRoll() + getSecondRoll() + pins > MAX_NUMBER_OF_PINS && finalTurnType == null)) {
            throw new IllegalRollException();
        }

        if (getTriesNum() == 0) {
            setFirstRoll(pins);
        } else if (getTriesNum() == 1) {
            setSecondRoll(pins);
        }
    }


    public int getRollBonus() {
        if (isStrike() && hasNextTurn()) {
            return nextTurn.getFirstRoll();
        } else {
            return getSecondRoll();
        }
    }

    public int calculateScore() {
        if (finalTurnType == null) {
            int turnScore = getFirstRoll() + getSecondRoll();
            if (isStrike() && hasNextTurn()) {
                return turnScore + nextTurn.getFirstRoll() + nextTurn.getRollBonus();
            } else if (isSpare() && hasNextTurn()) {
                return turnScore + nextTurn.getFirstRoll();
            } else {
                return turnScore;
            }
        }
        return 0;
    }


    public boolean hasNextTurn() {
        return nextTurn != null;
    }

    public boolean hasTurnRolls() {
        return !isStrike() && getTriesNum() < MAX_TRIES_IN_A_TURN;
    }
}
