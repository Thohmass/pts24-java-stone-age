package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public final class ResourceSource implements InterfaceFigureLocationInternal {

    private int resourcesRemaining;
    private final String name;
    private final Effect resource;
    private final int maxFigures;
    private final int maxFigureColours;
    private final ArrayList<PlayerOrder> figures;
    private final CurrentThrow currentThrow;

    public ResourceSource(final String name, final Effect resource, final int maxFigures, final int maxFigureColours,
                          final CurrentThrow currentThrow) {
        this.name = name;
        this.resource = resource;
        this.maxFigures = maxFigures;
        this.maxFigureColours = maxFigureColours;
        this.currentThrow = currentThrow;
        figures = new ArrayList<>();
    }

    @Override
    public boolean placeFigures(final Player player, final int figureCount) {
        if (tryToPlaceFigures(player, figureCount) == HasAction.NO_ACTION_POSSIBLE) {
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
        if (count <= 0 || !player.playerBoard().hasFigures(count) || figures.size() + count > maxFigures
                || figures.contains(player.playerOrder()) || figureColours() >= maxFigureColours) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    private int figureColours() {
        HashSet<PlayerOrder> set = new HashSet<>(figures);
        return set.size();
    }

    @Override
    public ActionResult makeAction(final Player player, final Effect[] inputResources, final Effect[] outputResources) {
        if (tryToMakeAction(player) == HasAction.NO_ACTION_POSSIBLE) {
            return ActionResult.FAILURE;
        }
        int figureCount = Collections.frequency(figures, player.playerOrder());
        // TODO - figure out current throw mechanic
        currentThrow.initiate(player, resource, figureCount);
        if (currentThrow.canUseTools()) {
            return ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE;
        }
        int quantity = Math.floorDiv(currentThrow.getThrowResult(), resource.points());
        Effect[] reward = new Effect[quantity];
        Arrays.fill(reward, resource);
        player.playerBoard().giveEffect(reward);
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
        int playerFiguresHere = Collections.frequency(figures, player.playerOrder());
        if (playerFiguresHere <= 0) {
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
