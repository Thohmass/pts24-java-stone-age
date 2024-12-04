package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.PlayerOrder;
import sk.uniba.fmph.dcs.stone_age.Location;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.Effect;

public interface InterfaceGamePhaseState {
    ActionResult placeFigures(PlayerOrder player, Location location, int figuresCount);

    ActionResult makeAction(PlayerOrder player, Location location, Effect[] inputResources, Effect[] outputResources);

    ActionResult skipAction(PlayerOrder player, Location location);

    ActionResult useTools(PlayerOrder player, int toolIndex);

    ActionResult noMoreToolsThisThrow(PlayerOrder player);

    ActionResult feedTribe(PlayerOrder player, Effect[] resources);

    ActionResult doNotFeedThisTurn(PlayerOrder player);

    ActionResult makeAllPlayersTakeARewardChoice(PlayerOrder player, Effect reward);

    HasAction tryToMakeAutomaticAction(PlayerOrder player);
}
