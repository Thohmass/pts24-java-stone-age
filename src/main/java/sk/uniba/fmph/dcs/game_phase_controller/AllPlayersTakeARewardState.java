package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;

public final class AllPlayersTakeARewardState implements InterfaceGamePhaseState {

    private final InterfaceTakeReward interfaceTakeReward;

    public AllPlayersTakeARewardState(InterfaceTakeReward interfaceTakeReward) {
        this.interfaceTakeReward = interfaceTakeReward;
    }

    @Override
    public ActionResult placeFigures(PlayerOrder player, Location location, int figuresCount) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult makeAction(PlayerOrder player, Location location, Collection<Effect> inputResources, Collection<Effect> outputResources) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult skipAction(PlayerOrder player, Location location) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult useTools(PlayerOrder player, int toolIndex) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult noMoreToolsThisThrow(PlayerOrder player) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult feedTribe(PlayerOrder player, Collection<Effect> resources) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult doNotFeedThisTurn(PlayerOrder player) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult makeAllPlayersTakeARewardChoice(PlayerOrder player, Effect reward) {
        if (interfaceTakeReward.takeReward(player, reward)) {
            return ActionResult.ACTION_DONE;
        }
        else return ActionResult.FAILURE;
    }

    @Override
    public HasAction tryToMakeAutomaticAction(PlayerOrder player) {
        return interfaceTakeReward.tryMakeAction(player);
    }
}
