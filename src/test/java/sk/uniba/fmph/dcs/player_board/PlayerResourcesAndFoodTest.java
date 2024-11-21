package sk.uniba.fmph.dcs.player_board;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;

import static org.junit.jupiter.api.Assertions.*;

class PlayerResourcesAndFoodTest {

    @Test
    public void itHasResources() {
        PlayerResourcesAndFood resourcesAndFood = new PlayerResourcesAndFood();
        assertTrue(resourcesAndFood.hasResources(new Effect[]{Effect.FOOD, Effect.FOOD}));

        resourcesAndFood.giveResources(new Effect[]{Effect.WOOD});
        assertTrue(resourcesAndFood.hasResources(new Effect[]{Effect.WOOD}));
    }

    @Test
    public void itDoesNotHaveResources() {
        PlayerResourcesAndFood resourcesAndFood = new PlayerResourcesAndFood();
        assertFalse(resourcesAndFood.hasResources(new Effect[]{Effect.WOOD}));
    }

    @Test
    public void itThrowsOnBadEffect() {
        PlayerResourcesAndFood resourcesAndFood = new PlayerResourcesAndFood();
        assertThrows(IllegalArgumentException.class, () -> resourcesAndFood.hasResources(new Effect[]{Effect.BUILDING}));
    }

    @Test
    public void itTakesResources() {
        PlayerResourcesAndFood resourcesAndFood = new PlayerResourcesAndFood();

        resourcesAndFood.takeResources(new Effect[]{Effect.FOOD, Effect.FOOD});
        assertEquals(10, new JSONObject(resourcesAndFood.state()).get("FOOD"));
    }

    @Test
    public void itDoesNotTakeIfLowOnResources() {
        PlayerResourcesAndFood resourcesAndFood = new PlayerResourcesAndFood();

        assertFalse(resourcesAndFood.takeResources(new Effect[]{Effect.WOOD}));
        assertEquals(0, new JSONObject(resourcesAndFood.state()).get("WOOD"));
    }

    @Test
    public void itThrowsOnTakingBadEffect() {
        PlayerResourcesAndFood resourcesAndFood = new PlayerResourcesAndFood();

        assertThrows(IllegalArgumentException.class, () -> resourcesAndFood.takeResources(new Effect[]{Effect.FOOD, Effect.BUILDING}));
        assertEquals(12, new JSONObject(resourcesAndFood.state()).get("FOOD"));
    }

    @Test
    public void itThrowsOnGivingBadEffect() {
        PlayerResourcesAndFood resourcesAndFood = new PlayerResourcesAndFood();

        assertThrows(IllegalArgumentException.class, () -> resourcesAndFood.giveResources(new Effect[]{Effect.BUILDING}));
    }

    @Test
    public void itGivesResources() {
        PlayerResourcesAndFood resourcesAndFood = new PlayerResourcesAndFood();
        resourcesAndFood.giveResources(new Effect[]{Effect.WOOD, Effect.WOOD, Effect.WOOD});

        assertEquals(3, new JSONObject(resourcesAndFood.state()).get("WOOD"));
    }

    @Test
    public void itCounts() {
        PlayerResourcesAndFood resourcesAndFood = new PlayerResourcesAndFood();
        resourcesAndFood.giveResources(new Effect[]{Effect.WOOD, Effect.WOOD, Effect.WOOD});

        assertEquals(3, resourcesAndFood.numberOfResourcesForFinalPoints());
    }
}