package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Arrays;
import java.util.Optional;

public final class CivilisationCardPlace implements InterFaceFigureLocationInternal {

    private final int requiredResources;
    private Optional<CivilisationCard> optionalCivilisationCard;
    private final CivilisationCardPlace nextCivilisationCardPlace;
    private final CivilisationCardDeck civilisationCardDeck;
    private PlayerOrder[] figures;
    private static final int MAX_FIGURES = 1;
    private static final int MAX_REQUIRED_RESOURCES = 4;

    public CivilisationCardPlace(final int requiredResources, final CivilisationCardDeck civilisationCardDeck,
                                 final GameBoard gameBoard) {
        this.requiredResources = requiredResources;
        this.civilisationCardDeck = civilisationCardDeck;

        if (requiredResources >= 1) {
            nextCivilisationCardPlace = new CivilisationCardPlace(requiredResources - 1,
                    civilisationCardDeck, gameBoard);
        } else {
            nextCivilisationCardPlace = null;
        }
        this.optionalCivilisationCard = civilisationCardDeck.getTop();
    }

    public String state() {
        return "Resources needed: "
                + requiredResources
                + "\nFigures now: "
                + Arrays.toString(figures);
    }

    @Override
    public boolean placeFigures(final Player player, final int figureCount) {
        if (figureCount != MAX_FIGURES || !player.playerBoard().hasFigures(figureCount) ||
                figures.length >= MAX_FIGURES) {
            return false;
        }
        figures = new PlayerOrder[]{player.playerOrder()};
        return false;
    }

    @Override
    public HasAction tryToPlaceFigures(final Player player, final int count) {
        if (count != MAX_FIGURES || !player.playerBoard().hasFigures(count) ||
                figures.length >= MAX_FIGURES) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.NO_ACTION_POSSIBLE;
    }

    @Override
    public ActionResult makeAction(final Player player, final Effect[] inputResources, final Effect[] outputResources) {
        if (optionalCivilisationCard.isEmpty()) {
            return ActionResult.FAILURE;
        }
        if (figures.length == 0 || player.playerOrder() != figures[0] || inputResources.length != requiredResources) {
            return ActionResult.FAILURE;
        }
        player.playerBoard().giveFigure();
        figures = new PlayerOrder[0];
        player.playerBoard().giveEffect(outputResources);
        player.playerBoard().giveEndOfGameEffect(optionalCivilisationCard.get().endOfGameEffect());
        return ActionResult.ACTION_DONE;
    }

    @Override
    public boolean skipAction(final Player player) {
        if (figures.length == 0 || player.playerOrder() != figures[0]) {
            return false;
        }
        player.playerBoard().giveFigure();
        figures = new PlayerOrder[0];
        return true;
    }

    @Override
    public HasAction tryToMakeAction(final Player player) {
        if (optionalCivilisationCard.isEmpty()) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        if (figures.length == 0 || player.playerOrder() != figures[0]) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public boolean newTurn() {
        if (figures.length != 0) {
            figures = new PlayerOrder[0];
        }
        while (nextCivilisationCardPlace != null && nextCivilisationCardPlace.optionalCivilisationCard.isEmpty()
                && optionalCivilisationCard.isPresent()) {
            nextCivilisationCardPlace.optionalCivilisationCard = optionalCivilisationCard;
            nextCivilisationCardPlace.newTurn();
            if (requiredResources == MAX_REQUIRED_RESOURCES) {
                optionalCivilisationCard = civilisationCardDeck.getTop();
            }
        }
        return optionalCivilisationCard.isPresent();
    }
}
