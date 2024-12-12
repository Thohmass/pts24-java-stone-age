package sk.uniba.fmph.dcs.stone_age;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class StoneAgeIntegrationTest {
    private StoneAgeGame game;
    private Map<Integer, PlayerOrder> players;


    // It is so hard to make these tests when some things just aint working___
    //
    // DISCLAIMER: These tests are form the game mechanics correct, but the game is returning wrong answers,
    // unfortunately
    //
    // That is why there is assertTrue or assertFalse on almost all game calls - to see, where the game is not working
    // the way it should


    void setUp(final int numberOfPlayers) throws IOException {
        // Set up a game with a specified number of players
        game = StoneAgeGameFactory.createStoneAgeGame(numberOfPlayers);

        // Prepare player orders
        players = new HashMap<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.put(i, new PlayerOrder(i, numberOfPlayers));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testPlacingZeroFigures(int numberOfPlayers) throws IOException {
        setUp(numberOfPlayers);
        assertFalse(game.placeFigures(0, Location.FOREST, 0));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testPlacingFiguresOnResourceSources(int numberOfPlayers) throws IOException {
        setUp(numberOfPlayers);

        // Test placing figures on different locations
        assertTrue(game.placeFigures(0, Location.FOREST, 2),
                "Player should be able to place 2 figures on the forest");

        assertTrue(game.placeFigures(1, Location.CLAY_MOUND, 1),
                "Player should be able to place 1 figure on the clay mound");

        if (numberOfPlayers > 2) {
            assertTrue(game.placeFigures(2, Location.CLAY_MOUND, 3),
                    "Player should be able to place 3 figures on clay mound");
        }
        if (numberOfPlayers > 3) {
            assertTrue(game.placeFigures(3, Location.CLAY_MOUND, 3),
                    "Player should be able to place 5 figures on the clay mound");
        }

        assertFalse(game.placeFigures(0, Location.CLAY_MOUND, 2),
                "Player should NOT be able to place 2 figures on the clay mound");

        assertFalse(game.placeFigures(0, Location.FOREST, 1),
                "Player should NOT be able to place figures on the forest again");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testToolMakerHutFields(int numberOfPlayers) throws IOException {
        setUp(numberOfPlayers);

        assertFalse(game.placeFigures(0, Location.TOOL_MAKER, 2),
                "Player should NOT be able to place 2 or more figures on the tool maker");
        assertTrue(game.placeFigures(0, Location.TOOL_MAKER, 1),
                "Player should be able to place 1 figure on the tool maker");
        assertFalse(game.placeFigures(1, Location.TOOL_MAKER, 1),
                "Player should not be able to place a figure on the tool maker if it is occupied");
        assertFalse(game.placeFigures(1, Location.HUT, 1),
                "Player should be NOT able to place 1 figure on the Hut");
        assertTrue(game.placeFigures(1, Location.HUT, 2),
                "Player should be able to place 2 figures on the Hut");
        if (numberOfPlayers == 3) {
            assertFalse(game.placeFigures(2, Location.FIELD, 1),
                    "Player should not be able to place figure on fields if it is the last one unoccupied" +
                            "from Fields, Tool maker and Hut in the game of three players");
        }
        if (numberOfPlayers == 4) {
            assertTrue(game.placeFigures(2, Location.FIELD, 1),
                    "Player should be able to place a figure on unoccupied Fields in game of four players");
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testFeedingTribe(int numberOfPlayers) throws IOException {
        setUp(numberOfPlayers);

        // First, place figures
        assertTrue(game.placeFigures(0, Location.FOREST, 5));
        assertTrue(game.placeFigures(1, Location.CLAY_MOUND, 5));
        assertTrue(game.placeFigures(2, Location.QUARRY, 5));
        assertTrue(game.placeFigures(3, Location.RIVER, 5));

        // Automatic actions will reward all players with resources from their resource sources.
        // Then feeding phase begins

        Effect[] food = new Effect[]{Effect.FOOD, Effect.FOOD, Effect.FOOD, Effect.FOOD, Effect.FOOD};
        assertFalse(game.feedTribe(0, new Effect[0]), "Player should NOT be able to feed the tribe" +
                "without using any resources");
        assertTrue(game.feedTribe(0, food),
                "First player should be able to feed their tribe with 5 food and have 7 food remaining");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testMakingActionOnResourceLocations(int numberOfPlayers) throws IOException {
        setUp(numberOfPlayers);

        // First, place figures
        assertTrue(game.placeFigures(0, Location.FOREST, 4));
        assertTrue(game.placeFigures(1, Location.CLAY_MOUND, 5));
        if (numberOfPlayers >= 3) {
            assertTrue(game.placeFigures(2, Location.QUARRY, 5));
        }
        if (numberOfPlayers >= 4) {
            assertTrue(game.placeFigures(3, Location.RIVER, 5));
        }
        assertTrue(game.placeFigures(0, Location.HUNTING_GROUNDS, 1));

        assertTrue(game.makeAction(0, Location.FOREST, new Effect[0], new Effect[]{Effect.WOOD}),
                "Player should be able to collect wood from forest");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testToolUsage(int numberOfPlayers) throws IOException {
        setUp(numberOfPlayers);

        // Simulate placing figures and making an action first
        game.placeFigures(0, Location.TOOL_MAKER, 1);
        game.placeFigures(1, Location.HUNTING_GROUNDS, 5);
        if (numberOfPlayers >= 3) {
            assertTrue(game.placeFigures(2, Location.QUARRY, 5));
        }
        if (numberOfPlayers >= 4) {
            assertTrue(game.placeFigures(3, Location.RIVER, 5));
        }
        game.placeFigures(0, Location.HUNTING_GROUNDS, 4);

        game.makeAction(0, Location.TOOL_MAKER, new Effect[0], new Effect[0]);
        game.makeAction(0, Location.HUNTING_GROUNDS, new Effect[0], new Effect[]{Effect.FOOD});

        // Use a tool
        assertTrue(game.useTools(0, 0),
                "Player should be able to use a tool");

        // Indicate no more tools will be used this turn
        assertTrue(game.noMoreToolsThisThrow(0),
                "Player should be able to finish using tools");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testFieldsWithFeeding(int numberOfPlayers) throws IOException {
        setUp(numberOfPlayers);

        assertTrue(game.placeFigures(0, Location.FIELD, 1));
        assertTrue(game.placeFigures(1, Location.HUNTING_GROUNDS, 5));
        if (numberOfPlayers >= 3) {
            assertTrue(game.placeFigures(2, Location.QUARRY, 5));
        }
        if (numberOfPlayers >= 4) {
            assertTrue(game.placeFigures(3, Location.RIVER, 5));
        }
        assertTrue(game.placeFigures(0, Location.HUNTING_GROUNDS, 4));

        assertTrue(game.makeAction(0, Location.FIELD, new Effect[0], new Effect[0]),
                "Player should be able to collect reward from fields");
        assertFalse(game.makeAction(0, Location.FIELD, new Effect[0], new Effect[0]),
                "Player should NOT be able to collect reward from fields more than once per round");

        // The rest of the actions should be done automatically

        assertTrue(game.feedTribe(0, new Effect[]{Effect.FOOD, Effect.FOOD, Effect.FOOD, Effect.FOOD}),
                "Player should be able to pay for feeding their tribe with four food now");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testCivilisationCardAcquisition(int numberOfPlayers) throws IOException {
        setUp(numberOfPlayers);

        // Place figures on civilisation card locations
        assertTrue(game.placeFigures(0, Location.CIVILISATION_CARD4, 1));
        assertFalse(game.placeFigures(1, Location.CIVILISATION_CARD4, 1),
                "Player should NOT be able to place figure on a civilisation card that already has a figure on it");
        assertTrue(game.placeFigures(1, Location.HUNTING_GROUNDS, 5));
        if (numberOfPlayers >= 3) {
            assertTrue(game.placeFigures(2, Location.QUARRY, 5));
        }
        if (numberOfPlayers >= 4) {
            assertTrue(game.placeFigures(3, Location.RIVER, 5));
        }
        assertTrue(game.placeFigures(0, Location.FOREST, 3));
        assertTrue(game.placeFigures(0, Location.HUNTING_GROUNDS, 1));


        assertTrue(game.makeAction(0, Location.FOREST, new Effect[0], new Effect[0]));

        // Attempt to acquire civilisation cards with required resources that the player collected right before
        Effect[] requiredResourcesCard4 = new Effect[]{Effect.WOOD};

        assertFalse(game.makeAction(0, Location.CIVILISATION_CARD4, new Effect[0], new Effect[0]),
                "Player should NOT be able to acquire fourth civilisation card by paying 0 resources");

        assertTrue(game.makeAction(0, Location.CIVILISATION_CARD4, requiredResourcesCard4, new Effect[0]),
                "Player should be able to acquire fourth civilisation card");

        assertFalse(game.makeAction(0, Location.CIVILISATION_CARD4, requiredResourcesCard4, new Effect[0]),
                "Player should NOT be able to acquire fourth civilisation card after he already acquired it");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testBuildingTilesAcquisition(int numberOfPlayers) throws IOException {
        setUp(numberOfPlayers);

        assertFalse(game.placeFigures(0, Location.BUILDING_TILE1, 2));
        assertTrue(game.placeFigures(0, Location.BUILDING_TILE1, 1));
        assertFalse(game.placeFigures(1, Location.BUILDING_TILE1, 1));
        assertTrue(game.placeFigures(1, Location.HUNTING_GROUNDS, 5));
        if (numberOfPlayers >= 3) {
            assertTrue(game.placeFigures(2, Location.QUARRY, 5));
        }
        if (numberOfPlayers >= 4) {
            assertTrue(game.placeFigures(3, Location.RIVER, 5));
        }
        assertTrue(game.placeFigures(0, Location.FOREST, 4));

        assertFalse(game.makeAction(0, Location.BUILDING_TILE1, new Effect[0], new Effect[0]),
                "Player should NOT be able to buy a building without paying any resources");
        assertTrue(game.makeAction(0, Location.BUILDING_TILE1, new Effect[]{Effect.WOOD}, new Effect[0]),
                "Player should be able to buy an arbitrary building with one wood");
        assertFalse(game.makeAction(0, Location.BUILDING_TILE1, new Effect[]{Effect.WOOD}, new Effect[0]),
                "Player should NOT be able to buy the same building with the same figure twice");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testNewRound(int numberOfPlayers) throws IOException{
        setUp(numberOfPlayers);

        for (int i = 0; i < numberOfPlayers; i++) {
            assertTrue(game.placeFigures(i, Location.HUNTING_GROUNDS, 5));
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            assertTrue(game.makeAction(i, Location.HUNTING_GROUNDS, new Effect[0], new Effect[0]));
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            assertTrue(game.feedTribe(i,
                    new Effect[]{Effect.FOOD, Effect.FOOD, Effect.FOOD, Effect.FOOD, Effect.FOOD}));
        }

        // New turn should start automatically

        for (int i = 0; i < numberOfPlayers; i++) {
            assertTrue(game.placeFigures(i, Location.HUNTING_GROUNDS, 5));
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            assertTrue(game.makeAction(i, Location.HUNTING_GROUNDS, new Effect[0], new Effect[0]));
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            assertTrue(game.feedTribe(i,
                    new Effect[]{Effect.FOOD, Effect.FOOD, Effect.FOOD, Effect.FOOD, Effect.FOOD}));
        }
    }
}
