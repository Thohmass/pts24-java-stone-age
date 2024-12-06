package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.ActionResult;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.Queue;
import java.util.Stack;

public final class BuildingTile implements InterfaceFigureLocationInternal {
    private Building building;
    private final Stack<Building> buildings;
    private final ArrayList<PlayerOrder> figures;
    private static final int MAX_FIGURES = 1;

    public BuildingTile(final Stack<Building> buildings) {
        this.buildings = new Stack<>();
        this.buildings.addAll(buildings);
        building = this.buildings.pop();
        this.figures = new ArrayList<>();
    }

    @Override
    public boolean placeFigures(final Player player, final int figureCount) {
        if (figureCount > MAX_FIGURES || !figures.isEmpty()) {
            return false;
        }
        if (!player.playerBoard().takeFigures(figureCount)) {
            return false;
        }
        figures.add(player.playerOrder());
        return true;
    }

    @Override
    public HasAction tryToPlaceFigures(final Player player, final int count) {
        if (!figures.isEmpty() || count > MAX_FIGURES || !player.playerBoard().hasFigures(count)) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public ActionResult makeAction(final Player player, final Effect[] inputResources, final Effect[] outputResources) {
        if (figures.isEmpty() || !figures.getFirst().equals(player.playerOrder())) {
            return ActionResult.FAILURE;
        }
        OptionalInt points = building.build(inputResources);
        if (points.isEmpty()) {
            return ActionResult.FAILURE;
        }
        player.playerBoard().giveEffect(outputResources);
        player.playerBoard().giveEffect(new Effect[]{Effect.BUILDING});
        if (!buildings.isEmpty()) {
            building = buildings.pop();
        }
        return ActionResult.ACTION_DONE;
    }

    @Override
    public boolean skipAction(final Player player) {
        if (figures.isEmpty() || !figures.getFirst().equals(player.playerOrder())) {
            return false;
        }
        figures.clear();
        return true;
    }

    @Override
    public HasAction tryToMakeAction(final Player player) {
        if (figures.isEmpty() || !figures.getFirst().equals(player.playerOrder())) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public boolean newTurn() {
        figures.clear();
        return true;
    }

    public String state() {
        return "Building: "
                + building.getClass().getSimpleName()
                + "\nFigures: "
                + figures;
    }
}
