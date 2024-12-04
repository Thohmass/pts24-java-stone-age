package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.InterfaceFigureLocation;
import sk.uniba.fmph.dcs.stone_age.InterfaceNewTurn;
import sk.uniba.fmph.dcs.stone_age.Location;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.Map;

public final class NewRoundState implements InterfaceGamePhaseState {
    private final InterfaceFigureLocation[] locations;
    private final Map<PlayerOrder, InterfaceNewTurn> playerNewTurnMap;

    public NewRoundState(final InterfaceFigureLocation[] locations,
            final Map<PlayerOrder, InterfaceNewTurn> playerNewTurnMap) {
        this.locations = locations;
        this.playerNewTurnMap = playerNewTurnMap;
    }

    @Override
    public ActionResult placeFigures(final PlayerOrder player, final Location location, final int figuresCount) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult makeAction(final PlayerOrder player, final Location location, final Effect[] inputResources,
            final Effect[] outputResources) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult skipAction(final PlayerOrder player, final Location location) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult useTools(final PlayerOrder player, final int toolIndex) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult noMoreToolsThisThrow(final PlayerOrder player) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult feedTribe(final PlayerOrder player, final Effect[] resources) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult doNotFeedThisTurn(final PlayerOrder player) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult makeAllPlayersTakeARewardChoice(final PlayerOrder player, final Effect reward) {
        return ActionResult.FAILURE;
    }

    @Override
    public HasAction tryToMakeAutomaticAction(final PlayerOrder player) {
        for (InterfaceFigureLocation location : locations) {
            if (location.newTurn()) {
                return HasAction.NO_ACTION_POSSIBLE;
            }
        }
        for (InterfaceNewTurn nt : playerNewTurnMap.values()) {
            try {
                nt.newTurn();
            } catch (Exception ignored) {
            }
        }
        return HasAction.AUTOMATIC_ACTION_DONE;
    }
}
