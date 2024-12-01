package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;
import sk.uniba.fmph.dcs.stone_age.InterfaceFeedTribe;
import sk.uniba.fmph.dcs.stone_age.InterfaceNewTurn;
import sk.uniba.fmph.dcs.stone_age.InterfacePlayerBoardGameBoard;

import javax.naming.CannotProceedException;
import java.util.Optional;

public final class PlayerBoardGameBoardFacade
        implements InterfacePlayerBoardGameBoard, InterfaceNewTurn, InterfaceFeedTribe {
    private final PlayerBoard playerBoard;

    private final int notFedPenalty = -10;

    public PlayerBoardGameBoardFacade(final PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    @Override
    public void giveEffect(final Effect[] stuff) {
        this.playerBoard.getResourcesAndFood().giveResources(stuff);
    }

    @Override
    public void giveEndOfGameEffect(final EndOfGameEffect[] stuff) {
        this.playerBoard.getCivilisationCards().addEndOfGameEffects(stuff);
    }

    @Override
    public boolean takeResources(final Effect[] stuff) {
        return playerBoard.getResourcesAndFood().takeResources(stuff);
    }

    @Override
    public boolean takeFigures(final int count) {
        return playerBoard.getFigures().takeFigures(count);
    }

    @Override
    public boolean hasFigures(final int count) {
        return playerBoard.getFigures().hasFigures(count);
    }

    @Override
    public boolean hasSufficientTools(final int goal) {
        return this.playerBoard.getTools().hasSufficientTools(goal);
    }

    @Override
    public Optional<Integer> useTool(final int idx) {
        return this.playerBoard.getTools().useTool(idx);
    }

    @Override
    public void newTurn() throws CannotProceedException {
        this.playerBoard.newTurn();
    }

    @Override
    public boolean feedTribeIfEnoughFood() {
        return playerBoard.getFedStatus().feedTribeIfEnoughFood();
    }

    @Override
    public boolean feedTribe(final Effect[] resources) {
        return playerBoard.getFedStatus().feedTribe(resources);
    }

    @Override
    public boolean doNotFeedThisTurn() {
        boolean hasBeenFed = playerBoard.getFedStatus().isTribeFed();

        if (!hasBeenFed) {
            playerBoard.addPoints(notFedPenalty);
        }

        return hasBeenFed;

    }

    @Override
    public boolean isTribeFed() {
        return playerBoard.getFedStatus().isTribeFed();
    }
}
