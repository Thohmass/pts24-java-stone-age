package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.*;

public class BuildingTile implements InterFaceFigureLocationInternal{
    private final Building building;
    private final ArrayList<PlayerOrder> figures;
    private static final int MAX_FIGURES = 1;

    public BuildingTile(Building building) {
        this.building = building;
        this.figures = new ArrayList<>();
    }

    @Override
    public boolean placeFigures(Player player, int figureCount) {
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
    public HasAction tryToPlaceFigures(Player player, int count) {
        if (!figures.isEmpty() || count > MAX_FIGURES || !player.playerBoard().hasFigures(count)) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public ActionResult makeAction(Player player, Effect[] inputResources, Effect[] outputResources) {
        if (figures.isEmpty() || !figures.getFirst().equals(player.playerOrder())) {
            return ActionResult.FAILURE;
        }
        OptionalInt points = building.build(inputResources);
        if (points.isEmpty()) {
            return ActionResult.FAILURE;
        }
        player.playerBoard().giveEffect(outputResources);
        player.playerBoard().giveEffect(new Effect[]{Effect.BUILDING});
        return ActionResult.ACTION_DONE;
    }

    @Override
    public boolean skipAction(Player player) {
        if (figures.isEmpty() || !figures.getFirst().equals(player.playerOrder())) {
            return false;
        }
        figures.clear();
        return true;
    }

    @Override
    public HasAction tryToMakeAction(Player player) {
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
        return "Building: " +
                building +
                "\nFigures: " +
                figures;
    }
}
