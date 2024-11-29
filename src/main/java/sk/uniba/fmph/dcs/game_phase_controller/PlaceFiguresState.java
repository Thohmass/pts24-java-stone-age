package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.InterfaceFigureLocation;
import sk.uniba.fmph.dcs.stone_age.Location;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;

import java.util.Map;
import java.util.Collection;

//this class represents figure placing and nothing else
public class PlaceFiguresState implements InterfaceGamePhaseState {

    private final Map<Location, InterfaceFigureLocation> places;

    public PlaceFiguresState(final Map<Location, InterfaceFigureLocation> places) {
        this.places = places; // player Has figures here
    }

    /**
     *
     * @param player
     *            - current player
     * @param location
     *            - location where player want to put figure/s on
     * @param figuresCount
     *            - number of figures that player want to place
     * @return - ActionResult of placing figures on location
     */

    @Override
    public ActionResult placeFigures(final PlayerOrder player, final Location location, final int figuresCount) {
        if (this.places.containsKey(location) && this.places.get(location).placeFigures(player, figuresCount)) {
            return ActionResult.ACTION_DONE;
        } else {
            return ActionResult.FAILURE;
        }
    }

    /**
     *
     * @param player
     *            - current player
     * @param location
     *            - location where player want to make action with figures
     * @param inputResources
     *            - resources that player want to spend
     * @param outputResources
     *            - resources that player acheive
     * @return - this class is not responsible for this method
     */

    @Override
    public ActionResult makeAction(final PlayerOrder player, final Location location,
            final Collection<Effect> inputResources, final Collection<Effect> outputResources) {
        return ActionResult.FAILURE;
    }

    /**
     *
     * @param player
     *            - current player
     * @param location
     *            - location where player want to skip action
     * @return - this class is not responsible for this method
     */

    @Override
    public ActionResult skipAction(final PlayerOrder player, final Location location) {
        return ActionResult.FAILURE;
    }

    /**
     *
     * @param player
     *            - current player
     * @param toolIndex
     *            - index of the tool which player want to use
     * @return - this class is not responsible for this method
     */

    @Override
    public ActionResult useTools(final PlayerOrder player, final int toolIndex) {
        return ActionResult.FAILURE;
    }

    /**
     *
     * @param player
     *            - current player
     * @return - this class is not responsible for this method
     */

    @Override
    public ActionResult noMoreToolsThisThrow(final PlayerOrder player) {
        return ActionResult.FAILURE;
    }

    /**
     *
     * @param player
     *            - current player
     * @param resources
     *            - food that player want to feed his tribe with
     * @return - this class is not responsible for this method
     */

    @Override
    public ActionResult feedTribe(final PlayerOrder player, final Collection<Effect> resources) {
        return ActionResult.FAILURE;
    }

    /**
     *
     * @param player
     *            - current player
     * @return - this class is not responsible for this method
     */

    @Override
    public ActionResult doNotFeedThisTurn(final PlayerOrder player) {
        return ActionResult.FAILURE;
    }

    /**
     *
     * @param player
     *            - current player
     * @param reward
     *            - reward that player want from action
     * @return - this class is not responsible for this method
     */

    @Override
    public ActionResult makeAllPlayersTakeARewardChoice(final PlayerOrder player, final Effect reward) {
        return ActionResult.FAILURE;
    }

    /**
     *
     * @param player
     *            - current player
     * @return - HasAction whether it is possible to make an automatic action
     */

    @Override
    public HasAction tryToMakeAutomaticAction(final PlayerOrder player) {
        HasAction hasAction = HasAction.NO_ACTION_POSSIBLE;
        for (Location location : this.places.keySet()) {
            hasAction = places.get(location).tryToPlaceFigures(player, 1);
            if (hasAction != HasAction.NO_ACTION_POSSIBLE) {
                return hasAction;
            }
        }
        return hasAction;
    }
}
