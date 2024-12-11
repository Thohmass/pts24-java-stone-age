package sk.uniba.fmph.dcs.stone_age;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import sk.uniba.fmph.dcs.game_board.GameBoard;
import sk.uniba.fmph.dcs.game_board.GameBoardFactory;
import sk.uniba.fmph.dcs.game_phase_controller.GamePhaseController;
import sk.uniba.fmph.dcs.game_phase_controller.GamePhase;

import java.util.Map;
import java.util.HashMap;

public class StoneAgeIntegrationTest {
    private StoneAgeGame game;
    private GameBoard gameBoard;
    private Map<Integer, PlayerOrder> players;

    @BeforeEach
    void setUp() {
        // Set up a 4-player game
        int numberOfPlayers = 4;
        gameBoard = GameBoardFactory.createGameBoard(numberOfPlayers);
        game = StoneAgeGameFactory.createStoneAgeGame(numberOfPlayers);

        // Prepare player orders
        players = new HashMap<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.put(i, new PlayerOrder(i, numberOfPlayers));
        }
    }

    @Test
    void testPlacingFiguresOnLocations() {
        // Test placing figures on different locations
        assertTrue(game.placeFigures(0, Location.FOREST, 2),
                "Player should be able to place 2 figures on the forest");

        assertTrue(game.placeFigures(1, Location.CLAY_MOUND, 1),
                "Player should be able to place 1 figure on the clay mound");

        assertTrue(game.placeFigures(2, Location.HUNTING_GROUNDS, 3),
                "Player should be able to place 3 figures on hunting grounds");
    }

    @Test
    void testMakingActionOnResourceLocations() {
        // First, place figures
        game.placeFigures(0, Location.FOREST, 2);
        game.placeFigures(1, Location.CLAY_MOUND, 1);

        // Then try to make actions
        Effect[] noInputResources = new Effect[0];
        Effect[] woodResources = new Effect[]{Effect.WOOD};

        assertTrue(game.makeAction(0, Location.FOREST, noInputResources, woodResources),
                "Player should be able to collect wood from forest");

        assertTrue(game.makeAction(1, Location.CLAY_MOUND, noInputResources, new Effect[]{Effect.CLAY}),
                "Player should be able to collect clay from clay mound");
    }

    @Test
    void testFeedingTribe() {
        // Test feeding tribe with various resources
        Effect[] foodResources = new Effect[]{Effect.FOOD, Effect.FOOD};
        assertTrue(game.feedTribe(0, foodResources),
                "Player should be able to feed tribe with food");

        // Test not feeding tribe
        assertTrue(game.doNotFeedThisTurn(1),
                "Player should be able to choose not to feed tribe");
    }

    @Test
    void testToolUsage() {
        // Simulate placing figures and making an action first
        game.placeFigures(0, Location.FOREST, 2);
        game.makeAction(0, Location.FOREST, new Effect[0], new Effect[]{Effect.WOOD});

        // Use a tool
        assertTrue(game.useTools(0, 0),
                "Player should be able to use a tool");

        // Indicate no more tools will be used this turn
        assertTrue(game.noMoreToolsThisThrow(0),
                "Player should be able to finish using tools");
    }

    @Test
    void testCivilisationCardAcquisition() {
        // Place figures on civilisation card locations
        game.placeFigures(0, Location.CIVILISATION_CARD1, 1);
        game.placeFigures(1, Location.CIVILISATION_CARD2, 1);

        // Attempt to acquire civilisation cards with required resources
        Effect[] requiredResourcesCard1 = new Effect[]{Effect.WOOD};
        Effect[] requiredResourcesCard2 = new Effect[]{Effect.WOOD, Effect.CLAY};

        assertTrue(game.makeAction(0, Location.CIVILISATION_CARD1, requiredResourcesCard1, new Effect[0]),
                "Player should be able to acquire first civilisation card");

        assertTrue(game.makeAction(1, Location.CIVILISATION_CARD2, requiredResourcesCard2, new Effect[0]),
                "Player should be able to acquire second civilisation card");
    }

    @Test
    void testRewardChoice() {
        // Simulate a reward choice scenario
        assertTrue(game.makeAllPlayerTakeARewardChoice(0, Effect.WOOD),
                "Player should be able to make a reward choice");
    }

    @Test
    void testGameProgression() {
        // This test ensures that game phases progress correctly
        // Simulate a basic round of gameplay
        game.placeFigures(0, Location.FOREST, 2);
        game.makeAction(0, Location.FOREST, new Effect[0], new Effect[]{Effect.WOOD});
        game.feedTribe(0, new Effect[]{Effect.FOOD});

        // These subsequent actions should now be for the next player
        assertTrue(game.placeFigures(1, Location.CLAY_MOUND, 1),
                "Next player should be able to place figures");
    }
}
