package sk.uniba.fmph.dcs.player_board;

import javax.naming.LimitExceededException;

public final class PlayerFigures {
    private int totalFigures;
    private int figures;

    private final int maxFigures = 10;
    private final int startFigures = 5;

    private boolean addedThisRound = false;

    public PlayerFigures() {
        this.totalFigures = startFigures;
        this.figures = this.totalFigures;
    }

    public void addNewFigure() throws LimitExceededException { // max 1 per round
        if (this.totalFigures < this.maxFigures) {
            if (!addedThisRound) {
                this.figures++;
                this.totalFigures++;
                this.addedThisRound = true;
            }
        } else {
            throw new LimitExceededException();
        }
    }

    public boolean hasFigures(final int count) {
        return count >= 0 && count <= this.figures;
    }

    public int getTotalFigures() {
        return totalFigures;
    }

    public boolean takeFigures(final int count) {
        if (hasFigures(count)) {
            figures -= count;
            return true;
        }
        return false;
    }

    public void newTurn() {
        this.figures = this.totalFigures;
        this.addedThisRound = false;
    }

    public String state() {
        return "";
    }
}
