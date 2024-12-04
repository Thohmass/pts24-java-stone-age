package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.InterfaceFigureLocation;
import sk.uniba.fmph.dcs.stone_age.Location;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//this class represents making possible actions and nothing else
public class MakeActionState implements InterfaceGamePhaseState {

    private final Map<Location, InterfaceFigureLocation> places;
    private List<Location> skipLocations; // locations that may be skipped

    public MakeActionState(final Map<Location, InterfaceFigureLocation> places) {
        this.places = places; // player Has figures here
        this.skipLocations = new ArrayList<>();
        this.skipLocations.add(Location.BUILDING_TILE1);
        this.skipLocations.add(Location.BUILDING_TILE2);
        this.skipLocations.add(Location.BUILDING_TILE3);
        this.skipLocations.add(Location.BUILDING_TILE4);
        this.skipLocations.add(Location.CIVILISATION_CARD1);
        this.skipLocations.add(Location.CIVILISATION_CARD2);
        this.skipLocations.add(Location.CIVILISATION_CARD3);
        this.skipLocations.add(Location.CIVILISATION_CARD4);
    }

    /**
     *
     * @param player
     *            - current player
     * @param location
     *            - where player want to place figure/s
     * @param figuresCount
     *            - number of figures
     *
     * @return - this class is not responsible for this method
     */
    @Override
    public ActionResult placeFigures(final PlayerOrder player, final Location location, final int figuresCount) {
        return ActionResult.FAILURE;
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
     *
     * @return - ActionResult of the action
     */

    @Override
    public ActionResult makeAction(final PlayerOrder player, final Location location, final Effect[] inputResources,
            final Effect[] outputResources) {
        if ((this.places.containsKey(location)) && (this.places.get(location) != null)) {
            ActionResult actionResult = this.places.get(location).makeAction(player, inputResources, outputResources);
            places.remove(location);
            return actionResult;
        }
        return ActionResult.FAILURE;
    }

    /**
     *
     * @param player
     *            - current player
     * @param location
     *            - location where player want to skip action
     *
     * @return - ActionResult of skipping action
     */

    @Override
    public ActionResult skipAction(final PlayerOrder player, final Location location) {
        if ((this.skipLocations.contains(location)) && this.places.get(location) != null) {
            places.remove(location);
            return ActionResult.ACTION_DONE;
        }
        return ActionResult.FAILURE;
    }

    /**
     *
     * @param player
     *            - current player
     * @param toolIndex
     *            - index of the tool which player want to use
     *
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
     *
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
     *
     * @return - this class is not responsible for this method
     */

    @Override
    public ActionResult feedTribe(final PlayerOrder player, final Effect[] resources) {
        return ActionResult.FAILURE;
    }

    /**
     *
     * @param player
     *            - current player
     *
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
     *
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
     *
     * @return - HasAction whether it is possible to make automatic action
     */

    @Override
    public HasAction tryToMakeAutomaticAction(final PlayerOrder player) {
        Location location = null;
        int actionsNumber = 0;
        for (Location locationFromKeySet : this.places.keySet()) {
            if (this.places.get(locationFromKeySet).tryToMakeAction(player) == HasAction.WAITING_FOR_PLAYER_ACTION) {
                actionsNumber++;
                location = locationFromKeySet;
            }
        }
        if (actionsNumber == 1) {
            this.places.get(location).makeAction(player, null, null);
            this.places.remove(location);
            return HasAction.AUTOMATIC_ACTION_DONE;
        } else if (actionsNumber == 0) {
            return HasAction.NO_ACTION_POSSIBLE;
        } else {
            return HasAction.WAITING_FOR_PLAYER_ACTION;
        }
    }
}
