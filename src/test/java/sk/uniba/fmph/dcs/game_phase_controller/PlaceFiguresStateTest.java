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

import static sk.uniba.fmph.dcs.stone_age.Location.TOOL_MAKER;

public class PlaceFiguresStateTest {

    private Location location;
    private PlayerOrder player;
    private PlaceFiguresState placeFiguresState;
    private Map<Location, InterfaceFigureLocation> places;

    @BeforeEach
    public void setUp() {
        location = TOOL_MAKER;
        player = new PlayerOrder(0, 0);
        InterfaceFigureLocation figureLocation = new InterfaceFigureLocation() {
            @Override
            public boolean placeFigures(PlayerOrder player, int figureCount) {
                return figureCount > 0;
            }

            @Override
            public HasAction tryToPlaceFigures(PlayerOrder player, int count) {
                return null;
            }

            @Override
            public ActionResult makeAction(PlayerOrder player, Effect[] inputResources, Effect[] outputResources) {
                return null;
            }

            @Override
            public boolean skipAction(PlayerOrder player) {
                return false;
            }

            @Override
            public HasAction tryToMakeAction(PlayerOrder player) {
                return null;
            }

            @Override
            public boolean newTurn() {
                return false;
            }
        };
        places = new HashMap<>();
        places.put(location, figureLocation);

        placeFiguresState = new PlaceFiguresState(places);
    }

    @Test
    public void testPlaceFigureSuccessful() {
        ActionResult result = placeFiguresState.placeFigures(player, location, 1);
        assertEquals(ActionResult.ACTION_DONE, result);
    }

    @Test
    public void testPlaceFigureUnsuccessful() {
        ActionResult result = placeFiguresState.placeFigures(player, location, 0);
        assertEquals(ActionResult.FAILURE, result);
    }
}
