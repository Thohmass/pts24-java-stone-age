package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;
import java.util.Map;

public final class  FeedTribeState implements InterfaceGamePhaseState{

    private final Map<PlayerOrder, InterfaceFeedTribe> interfaceFeedTribeCollection;

    public FeedTribeState(Map<PlayerOrder, InterfaceFeedTribe> interfaceFeedTribeCollection) {
        this.interfaceFeedTribeCollection = interfaceFeedTribeCollection;
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
    public ActionResult feedTribe(PlayerOrder player, final Collection<Effect> resources) {
        if(interfaceFeedTribeCollection.get(player).feedTribe(resources)) {
            return ActionResult.ACTION_DONE;
        }
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult doNotFeedThisTurn(PlayerOrder player) {
        if(interfaceFeedTribeCollection.get(player).doNotFeedThisTurn()) {
            return ActionResult.ACTION_DONE;
        }
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult makeAllPlayersTakeARewardChoice(PlayerOrder player, Effect reward) {
        return ActionResult.FAILURE;
    }

    @Override
    public HasAction tryToMakeAutomaticAction(PlayerOrder player) {
        if(interfaceFeedTribeCollection.get(player).isTribeFed()) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        if(interfaceFeedTribeCollection.get(player).feedTribeIfEnoughFood()) {
            return HasAction.AUTOMATIC_ACTION_DONE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;

    }
}
