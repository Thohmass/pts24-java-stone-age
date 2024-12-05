package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import java.util.Collections;

public final class ResourceSource implements InterFaceFigureLocationInternal{

    private final String name;
    private final Effect resource;
    private final int maxFigures;
    private final int maxFigureColours;
    private final ArrayList<PlayerOrder> figures;
    private CurrentThrow currentThrow;

    public ResourceSource(final String name, final Effect resource, final int maxFigures, final int maxFigureColours) {
        this.name = name;
        this.resource = resource;
        this.maxFigures = maxFigures;
        this.maxFigureColours = maxFigureColours;
        figures = new ArrayList<>();
    }

    @Override
    public boolean placeFigures(final Player player, final int figureCount) {
        if (!player.playerBoard().hasFigures(figureCount)) {
            return false;
        }
        if (figures.size() + figureCount > maxFigures) {
            return false;
        }
        player.playerBoard().takeFigures(figureCount);
        for (int i = 0; i < figureCount; i++) {
            figures.add(player.playerOrder());
        }
        return true;
    }

    @Override
    public HasAction tryToPlaceFigures(final Player player, final int count) {
        if (!player.playerBoard().hasFigures(count) || figures.size() + count > maxFigures
                || figures.contains(player.playerOrder())) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public ActionResult makeAction(final Player player, final Effect[] inputResources, final Effect[] outputResources) {
        int figureCount = Collections.frequency(figures, player.playerOrder());
        if (figureCount <= 0) {
            return ActionResult.FAILURE;
        }
        if (currentThrow == null) {
            currentThrow = new CurrentThrow(resource);
            currentThrow.initiate(player, resource, figureCount);
            if (currentThrow.canUseTools()) {
                return ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE;
            }
        }
        // TODO Tool Use
        player.playerBoard().giveEffect(outputResources);
        for (int i = 0; i < figureCount; i++) {
            player.playerBoard().giveFigure();
            figures.remove(player.playerOrder());
        }
        return ActionResult.ACTION_DONE;
    }

    @Override
    public boolean skipAction(final Player player) {
        if (!figures.contains(player.playerOrder())) {
            return false;
        }
        while (figures.contains(player.playerOrder())) {
            figures.remove(player.playerOrder());
            player.playerBoard().giveFigure();
        }
        return true;
    }

    @Override
    public HasAction tryToMakeAction(final Player player) {
        if (!player.playerBoard().hasFigures(1) || figures.contains(player.playerOrder())
                || figures.size() >= maxFigures) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public boolean newTurn() {
        figures.clear();
        return false;
    }

    public String state() {
        return "Name: "
                + name
                + "\nResource type: "
                + resource.toString()
                + "\nNumber of figures: "
                + figures.size()
                + "/"
                + maxFigures;
    }
}
