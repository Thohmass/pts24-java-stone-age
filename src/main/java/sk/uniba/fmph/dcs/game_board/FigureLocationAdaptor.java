package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.InterfaceFigureLocation;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.List;

public final class FigureLocationAdaptor implements InterfaceFigureLocation {
    private final InterFaceFigureLocationInternal figureLocationInternal;
    private final List<Player> players;

    public FigureLocationAdaptor(final InterFaceFigureLocationInternal figureLocationInternal,
            final List<Player> players) {
        this.figureLocationInternal = figureLocationInternal;
        this.players = players;
    }

    @Override
    public boolean placeFigures(final PlayerOrder player, final int figureCount) {
        if (getPlayerOrder(player) != null) {
            return figureLocationInternal.placeFigures(getPlayerOrder(player), figureCount);
        }
        return false;
    }

    @Override
    public HasAction tryToPlaceFigures(final PlayerOrder player, final int count) {
        if (getPlayerOrder(player) != null) {
            return figureLocationInternal.tryToPlaceFigures(getPlayerOrder(player), count);
        }
        return null;
    }

    @Override
    public ActionResult makeAction(final PlayerOrder player, final Effect[] inputResources,
            final Effect[] outputResources) {
        if (getPlayerOrder(player) != null) {
            return figureLocationInternal.makeAction(getPlayerOrder(player), inputResources, outputResources);
        }
        return null;
    }

    @Override
    public boolean skipAction(final PlayerOrder player) {
        if (getPlayerOrder(player) != null) {
            figureLocationInternal.skipAction(getPlayerOrder(player));
        }
        return false;
    }

    @Override
    public HasAction tryToMakeAction(final PlayerOrder player) {
        if (getPlayerOrder(player) != null) {
            figureLocationInternal.tryToMakeAction(getPlayerOrder(player));
        }
        return null;
    }

    @Override
    public boolean newTurn() {
        return figureLocationInternal.newTurn();
    }

    private Player getPlayerOrder(final PlayerOrder player) {
        for (Player pl : players) {
            if (pl.playerOrder().equals(player)) {
                return pl;
            }
        }
        return null;
    }
}
