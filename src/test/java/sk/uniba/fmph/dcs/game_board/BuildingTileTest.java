package sk.uniba.fmph.dcs.game_board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.OptionalInt;

public class BuildingTileTest {
    // Test doubles
    private static class TestBuilding implements Building {
        private boolean buildCalled = false;

        @Override
        public OptionalInt build(Effect[] inputResources) {
            buildCalled = true;
            return OptionalInt.of(5);
        }

        public boolean wasBuildCalled() {
            return buildCalled;
        }
    }

    private static class TestPlayerBoard implements InterfacePlayerBoardGameBoard {
        private boolean takeFiguresCalled = false;
        private boolean hasFiguresCalled = false;
        private boolean giveEffectCalled = false;

        @Override
        public void giveEffect(Effect[] stuff) {
            giveEffectCalled = true;
        }

        @Override
        public void giveEndOfGameEffect(EndOfGameEffect[] stuff) {
            // Not needed for this test
        }

        @Override
        public boolean takeResources(Effect[] stuff) {
            return true;
        }

        @Override
        public boolean giveFigure() {
            return true;
        }

        @Override
        public boolean takeFigures(int count) {
            takeFiguresCalled = true;
            return true;
        }

        @Override
        public boolean hasFigures(int count) {
            hasFiguresCalled = true;
            return true;
        }

        @Override
        public boolean hasSufficientTools(int goal) {
            return true;
        }

        @Override
        public java.util.Optional<Integer> useTool(int idx) {
            return java.util.Optional.empty();
        }

        public boolean wasTakeFiguresCalled() {
            return takeFiguresCalled;
        }

        public boolean wasHasFiguresCalled() {
            return hasFiguresCalled;
        }

        public boolean wasGiveEffectCalled() {
            return giveEffectCalled;
        }
    }

    private BuildingTile buildingTile;
    private TestBuilding testBuilding;
    private TestPlayerBoard testPlayerBoard;
    private Player player;

    @BeforeEach
    public void setUp() {
        // Create test doubles
        testBuilding = new TestBuilding();
        testPlayerBoard = new TestPlayerBoard();
        player = new Player(new PlayerOrder(0, 2), testPlayerBoard);

        // Create BuildingTile with test building
        buildingTile = new BuildingTile(testBuilding);
    }

    @Test
    public void testState() {
        assertEquals("Building: TestBuilding\nFigures: []", buildingTile.state());
    }
    
    @Test
    public void testPlaceFigures_Success() {
        // Act
        boolean result = buildingTile.placeFigures(player, 1);

        // Assert
        assertTrue(result);
        assertTrue(testPlayerBoard.wasTakeFiguresCalled());
    }

    @Test
    public void testPlaceFigures_TooManyFigures() {
        // Act
        boolean result = buildingTile.placeFigures(player, 2);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testPlaceFigures_AlreadyOccupied() {
        // Arrange
        buildingTile.placeFigures(player, 1);

        // Act
        boolean result = buildingTile.placeFigures(player, 1);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testTryToPlaceFigures_Success() {
        // Act
        HasAction result = buildingTile.tryToPlaceFigures(player, 1);

        // Assert
        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, result);
        assertTrue(testPlayerBoard.wasHasFiguresCalled());
    }

    @Test
    public void testTryToPlaceFigures_AlreadyOccupied() {
        // Arrange
        buildingTile.placeFigures(player, 1);

        // Act
        HasAction result = buildingTile.tryToPlaceFigures(player, 1);

        // Assert
        assertEquals(HasAction.NO_ACTION_POSSIBLE, result);
    }

    @Test
    public void testMakeAction_Success() {
        // Arrange
        buildingTile.placeFigures(player, 1);
        Effect[] inputResources = {Effect.WOOD};
        Effect[] outputResources = {Effect.BUILDING};

        // Act
        ActionResult result = buildingTile.makeAction(player, inputResources, outputResources);

        // Assert
        assertEquals(ActionResult.ACTION_DONE, result);
        assertTrue(testBuilding.wasBuildCalled());
        assertTrue(testPlayerBoard.wasGiveEffectCalled());
    }

    @Test
    public void testMakeAction_WrongPlayer() {
        // Arrange
        buildingTile.placeFigures(player, 1);
        Player otherPlayer = new Player(new PlayerOrder(1, 2), testPlayerBoard);
        Effect[] inputResources = {Effect.WOOD};
        Effect[] outputResources = {Effect.BUILDING};

        // Act
        ActionResult result = buildingTile.makeAction(otherPlayer, inputResources, outputResources);

        // Assert
        assertEquals(ActionResult.FAILURE, result);
    }

    @Test
    public void testSkipAction_Success() {
        // Arrange
        buildingTile.placeFigures(player, 1);

        // Act
        boolean result = buildingTile.skipAction(player);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testSkipAction_WrongPlayer() {
        // Arrange
        buildingTile.placeFigures(player, 1);
        Player otherPlayer = new Player(new PlayerOrder(1, 2), testPlayerBoard);

        // Act
        boolean result = buildingTile.skipAction(otherPlayer);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testNewTurn_ClearFigures() {
        // Arrange
        buildingTile.placeFigures(player, 1);

        // Act
        boolean result = buildingTile.newTurn();

        // Assert
        assertTrue(result);
    }
}