package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.InterfaceFigureLocation;
import sk.uniba.fmph.dcs.stone_age.Location;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MakeActionStateTest {

    private Location location;
    private PlayerOrder player;
    private MakeActionState makeActionState;
    private Map<Location, InterfaceFigureLocation> places;
    private boolean actionSuccess;

    @BeforeEach
    public void setUp() {
        location = Location.TOOL_MAKER;
        player = new PlayerOrder(0, 0);
        InterfaceFigureLocation figureLocation = new InterfaceFigureLocation() {
            @Override
            public boolean placeFigures(PlayerOrder player, int figureCount) {
                return false;
            }

            @Override
            public HasAction tryToPlaceFigures(PlayerOrder player, int count) {
                return null;
            }

            @Override
            public ActionResult makeAction(PlayerOrder player, Effect[] inputResources, Effect[] outputResources) {
                if (actionSuccess) {
                    return ActionResult.ACTION_DONE;
                } else {
                    return ActionResult.FAILURE;
                }
            }

            @Override
            public boolean skipAction(PlayerOrder player) {
                return false;
            }

            @Override
            public HasAction tryToMakeAction(PlayerOrder player) {
                return HasAction.NO_ACTION_POSSIBLE;
            }

            @Override
            public boolean newTurn() {
                return false;
            }
        };
        places = new HashMap<>();
        places.put(location, figureLocation);
        makeActionState = new MakeActionState(places);
    }

    // Successful action
    @Test
    public void testMakeActionSuccesful() {
        actionSuccess = true;
        ActionResult result = makeActionState.makeAction(player, location, null, null);
        assertEquals(ActionResult.ACTION_DONE, result);
    }

    // Unsuccessful action
    @Test
    public void testMakeActionUnsuccessful() {
        ActionResult result = makeActionState.makeAction(player, location, null, null);
        assertEquals(ActionResult.FAILURE, result);
    }
}
