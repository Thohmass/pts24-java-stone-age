package sk.uniba.fmph.dcs.game_board;

import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

public class ArbitraryBuildingTest {

    @Test
    public void itDoesNotGivePointsForInvalidResources() {
        Building building = new ArbitraryBuilding();
        assertEquals(OptionalInt.empty(), building.build(new ArrayList<>()));
        assertEquals(OptionalInt.empty(), building.build(List.of(new Effect[] { Effect.WOOD, Effect.CLAY, Effect.WOOD,
                Effect.CLAY, Effect.WOOD, Effect.CLAY, Effect.WOOD, Effect.CLAY })));
    }

    @Test
    public void itGivesPointsForValidResources() {
        Building building = new ArbitraryBuilding();
        assertEquals(OptionalInt.of(3), building.build(List.of(new Effect[] { Effect.WOOD })));
        assertEquals(OptionalInt.of(25), building.build(List.of(new Effect[] { Effect.CLAY, Effect.WOOD, Effect.CLAY,
                Effect.WOOD, Effect.CLAY, Effect.WOOD, Effect.CLAY })));
    }

    @Test
    public void itDoesNotGivePointsForInvalidEffects() {
        Building building = new ArbitraryBuilding();
        assertEquals(OptionalInt.empty(), building.build(List.of(new Effect[] { Effect.BUILDING })));
    }
}
