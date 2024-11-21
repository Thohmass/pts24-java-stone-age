package sk.uniba.fmph.dcs.game_board;

import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.List;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

public class VariableBuildingTest {
    @Test
    public void itConstructsWithinRestrains() {
        assertThrows(IllegalArgumentException.class, () -> new VariableBuilding(0, 0));
        assertThrows(IllegalArgumentException.class, () -> new VariableBuilding(1, 2));
        assertThrows(IllegalArgumentException.class, () -> new VariableBuilding(6, 2));

        assertDoesNotThrow(() -> new VariableBuilding(5, 5));
        assertDoesNotThrow(() -> new VariableBuilding(1, 1));
    }

    @Test
    public void itDoesNotBuildWithInvalidNumberOfResourcesOrResourceTypes() {
        Building building = new VariableBuilding(1, 1);

        assertEquals(OptionalInt.empty(), building.build(List.of()));
        assertEquals(OptionalInt.empty(), building.build(List.of(new Effect[] { Effect.WOOD, Effect.WOOD })));

        Building building2 = new VariableBuilding(3, 2);
        assertEquals(OptionalInt.empty(),
                building2.build(List.of(new Effect[] { Effect.CLAY, Effect.WOOD, Effect.STONE })));
        assertEquals(OptionalInt.empty(),
                building2.build(List.of(new Effect[] { Effect.CLAY, Effect.CLAY, Effect.CLAY })));
    }

    @Test
    public void itGivesPointsWhenExactNumberOfResourcesOrResourceTypes() {
        Building building = new VariableBuilding(3, 2);

        assertEquals(OptionalInt.of(10),
                building.build(List.of(new Effect[] { Effect.WOOD, Effect.CLAY, Effect.WOOD })));

    }
}
