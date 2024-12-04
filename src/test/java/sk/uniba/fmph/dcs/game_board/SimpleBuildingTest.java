package sk.uniba.fmph.dcs.game_board;

import java.util.OptionalInt;

import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleBuildingTest {
    @Test
    public void test_calculation() {
        Effect[] buildingResources = new Effect[]{Effect.WOOD};

        SimpleBuilding building = new SimpleBuilding(buildingResources);

        assertEquals(building.build(buildingResources), OptionalInt.of(3));

        Effect[] otherResources = new Effect[]{Effect.WOOD, Effect.WOOD};
        assertEquals(building.build(otherResources), OptionalInt.empty());
    }
}
