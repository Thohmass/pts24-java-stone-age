package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;

public final class PlaceOnToolMakerAdaptor implements InterFaceFigureLocationInternal {
    private final ToolMakerHutFields tools;

    public PlaceOnToolMakerAdaptor(ToolMakerHutFields tools) {
        this.tools = tools;
    }

    @Override
    public boolean placeFigures(final Player player, final int figureCount) {
        if (tryToPlaceFigures(player, figureCount) == HasAction.NO_ACTION_POSSIBLE) {
            return false;
        }
        return tools.placeOnToolMaker(player);
    }

    @Override
    public HasAction tryToPlaceFigures(final Player player, final int count) {
        if (count == 1 && player.playerBoard().hasFigures(count) && tools.canPlaceOnToolMaker(player)) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public ActionResult makeAction(final Player player, final Effect[] inputResources, final Effect[] outputResources) {
        if (tryToMakeAction(player) == HasAction.NO_ACTION_POSSIBLE) {
            return ActionResult.FAILURE;
        }
        return tools.actionToolMaker(player) ? ActionResult.ACTION_DONE : ActionResult.FAILURE;
    }

    @Override
    public boolean skipAction(final Player player) {
        return true;
    }

    @Override
    public HasAction tryToMakeAction(final Player player) {
        return tools.getCanToolMaker() ? HasAction.WAITING_FOR_PLAYER_ACTION : HasAction.NO_ACTION_POSSIBLE;
    }

    @Override
    public boolean newTurn() {
        return tools.newTurn();
    }
}
