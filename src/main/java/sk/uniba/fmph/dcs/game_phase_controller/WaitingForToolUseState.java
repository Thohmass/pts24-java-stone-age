package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.InterfaceToolUse;
import sk.uniba.fmph.dcs.stone_age.Location;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.Map;

public final class WaitingForToolUseState implements InterfaceGamePhaseState {
    private final Map<PlayerOrder, InterfaceToolUse> toolUseMap;

    public WaitingForToolUseState(final Map<PlayerOrder, InterfaceToolUse> toolUseMap) {
        this.toolUseMap = toolUseMap;
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
        if (toolUseMap.get(player).useTool(toolIndex)) {
            return ActionResult.ACTION_DONE;
        } else {
            return ActionResult.FAILURE;
        }
    }

    @Override
    public ActionResult noMoreToolsThisThrow(final PlayerOrder player) {
        if (toolUseMap.get(player).finishUsingTools()) {
            return ActionResult.ACTION_DONE;
        } else {
            return ActionResult.FAILURE;
        }
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
        if (toolUseMap.get(player) == null || !toolUseMap.get(player).canUseTools()) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        if (toolUseMap.get(player).finishUsingTools()) {
            return HasAction.AUTOMATIC_ACTION_DONE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }
}
